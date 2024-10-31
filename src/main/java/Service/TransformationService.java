package Service;

import Model.Aeroporto;
import Model.Estado;
import Model.Pais;
import Model.VooAnac;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TransformationService {

    public List<Pais> TransformarPaises(List<VooAnac> voos) {
        AtomicInteger idGenerator = new AtomicInteger(1);
        return voos.stream()
                .filter(voo -> voo.getPaisOrigem() != null && !voo.getPaisOrigem().isEmpty())
                .collect(Collectors.toMap(
                        VooAnac::getPaisOrigem,
                        VooAnac::getContinentePaisOrigem,
                        (continenteExistente, novoContinente) -> continenteExistente
                ))
                .entrySet().stream()
                .map(entry -> new Pais(
                        idGenerator.getAndIncrement(),
                        entry.getKey(),
                        entry.getValue()
                ))
                .collect(Collectors.toList());
    }

    public List<Estado> TransformarEstados(List<VooAnac> voos) {
        return voos.stream()
                .filter(voo -> voo.getUfAeroportoOrigem() != null && !voo.getUfAeroportoOrigem().isEmpty())
                .collect(Collectors.toMap(
                        VooAnac::getUfAeroportoOrigem,
                        voo -> new Estado(voo.getUfAeroportoOrigem(), voo.getRegiaoAeroportoOrigem()),
                        (estadoExistente, estadoNovo) -> estadoExistente
                ))
                .values().stream()
                .collect(Collectors.toList());
    }

    public List<Aeroporto> TransformarAeroportos(List<VooAnac> voos, List<Pais> paises, List<Estado> estados) {
        Map<String, Integer> idPaises = paises.stream()
                .collect(Collectors.toMap(Pais::getNome, Pais::getId));

        Map<String, Estado> codigoEstados = estados.stream()
                .collect(Collectors.toMap(Estado::getNome, Function.identity()));

        Map<String, VooAnac> aeroportosMap = voos.stream()
                .filter(voo -> voo.getAeroportoOrigem() != null && !voo.getAeroportoOrigem().isEmpty())
                .collect(Collectors.toMap(
                        VooAnac::getAeroportoOrigem,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));

        AtomicInteger aeroportoIdGenerator = new AtomicInteger(1);
        return aeroportosMap.entrySet().stream()
                .map(entry -> {
                    String nomeAeroporto = entry.getKey();
                    VooAnac voo = entry.getValue();
                    String paisOrigem = voo.getPaisOrigem();
                    Integer fkPais = idPaises.get(paisOrigem);

                    Integer fkEstado = null;
                    if ("Brasil".equalsIgnoreCase(paisOrigem)) {
                        String ufOrigem = voo.getUfAeroportoOrigem();
                        Estado estado = codigoEstados.get(ufOrigem);
                        if (estado != null) {
                            fkEstado = estado.getCodigoIbge();
                        }
                    }

                    return new Aeroporto(
                            aeroportoIdGenerator.getAndIncrement(),
                            nomeAeroporto,
                            fkPais,
                            fkEstado
                    );
                })
                .filter(aeroporto -> aeroporto.getFkPais() != null)
                .collect(Collectors.toList());
    }
}