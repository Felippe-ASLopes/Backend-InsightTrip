package Service;

import Model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

//    Método para base "BancoVDE 2023.xlxs", que foi substituida
//    public List<Crime> AgregarCrimes(List<Crime> crimes) {
//        Map<String, Crime> mapaCrimes = new HashMap<>();
//
//        for (Crime crime : crimes) {
//            String nome = crime.getNome();
//            Integer estado = crime.getEstado();
//            LocalDate data = crime.getData();
//
//
//            String chave = nome + "_" + estado + "_" + data.getMonthValue() + "_" + data.getYear();
//
//            if (mapaCrimes.containsKey(chave)) {
//                Crime existente = mapaCrimes.get(chave);
//                existente.setQtdOcorrencia(existente.getQtdOcorrencia() + 1);
//            } else {
//                Crime novoCrime = new Crime(nome, data, estado, 1);
//                mapaCrimes.put(chave, novoCrime);
//            }
//        }
//
//        return new ArrayList<>(mapaCrimes.values());
//    }

    public static int converterMes(String mes) {
        return switch (mes.toLowerCase()) {
            case "janeiro" -> 1;
            case "fevereiro" -> 2;
            case "março", "marco" -> 3;
            case "abril" -> 4;
            case "maio" -> 5;
            case "junho" -> 6;
            case "julho" -> 7;
            case "agosto" -> 8;
            case "setembro" -> 9;
            case "outubro" -> 10;
            case "novembro" -> 11;
            case "dezembro" -> 12;
            default -> -1;
        };
    }
}