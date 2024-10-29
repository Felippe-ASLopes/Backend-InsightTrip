package Utils;

import Objetos.Estado;
import Objetos.Pais;
import Objetos.VooAnac;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SqlUtils {
    public static String ConstruirInsertPais(List<Pais> paisesUnicos) {
        if (paisesUnicos == null || paisesUnicos.isEmpty()) {
            return "";
        }

        StringBuilder sql = new StringBuilder("INSERT INTO Pais (Nome, Continente) VALUES ");

        List<String> valores = paisesUnicos.stream()
                .map(pais -> String.format("('%s', '%s')",
                        escaparString(pais.getNome()),
                        escaparString(pais.getContinente())))
                .collect(Collectors.toList());

        sql.append(String.join(", ", valores));
        sql.append(";");

        return sql.toString();
    }

    public static String ConstruirInsertEstado(List<Estado> estadosUnicos) {
        if (estadosUnicos == null || estadosUnicos.isEmpty()) {
            return "";
        }

        StringBuilder sql = new StringBuilder("INSERT INTO UF (CodigoIBGE, Nome, Regiao) VALUES ");

        List<String> valores = estadosUnicos.stream()
                .map(estado -> String.format("(%d, '%s', '%s')",
                        estado.getCodigoIbge(),
                        escaparString(estado.getNome()),
                        escaparString(estado.getRegiao()))
                )
                .collect(Collectors.toList());

        sql.append(String.join(", ", valores));
        sql.append(";");

        return sql.toString();
    }

    public static String ConstruirInsertAeroporto(
            List<VooAnac> viagensExtraidas,
            List<Pais> paisesUnicos,
            List<Estado> estadosUnicos
    ) {
        if (viagensExtraidas == null || viagensExtraidas.isEmpty() ||
                paisesUnicos == null || paisesUnicos.isEmpty()) {
            return "";
        }

        StringBuilder sql = new StringBuilder("INSERT INTO Aeroporto (NomeAeroporto, fkPais, fkEstado) VALUES ");

        Map<String, Integer> idPaises = paisesUnicos.stream()
                .collect(Collectors.toMap(Pais::getNome, Pais::getId));

        Map<String, Estado> codigoEstados = estadosUnicos.stream()
                .collect(Collectors.toMap(Estado::getNome, Function.identity()));

        Map<String, VooAnac> aeroportos = viagensExtraidas.stream()
                .filter(voo -> voo.getAeroportoOrigem() != null && !voo.getAeroportoOrigem().isEmpty())
                .collect(Collectors.toMap(
                        VooAnac::getAeroportoOrigem,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));

        if (aeroportos.isEmpty()) {
            return "";
        }

        List<String> valores = aeroportos.entrySet().stream()
                .map(entry -> {
                    String nomeAeroporto = escaparString(entry.getKey());
                    VooAnac voo = entry.getValue();
                    String paisOrigem = voo.getPaisOrigem();
                    Integer fkPais = idPaises.get(paisOrigem);

                    if (fkPais == null) {
                        return null;
                    }

                    if (paisOrigem.equalsIgnoreCase("Brasil")) {
                        String ufOrigem = voo.getUfAeroportoOrigem();
                        Estado estado = codigoEstados.get(ufOrigem);
                        if (estado != null && estado.getCodigoIbge() != null) {
                            return String.format("('%s', %d, %d)",
                                    nomeAeroporto,
                                    fkPais,
                                    estado.getCodigoIbge());
                        } else {
                            return String.format("('%s', %d, NULL)",
                                    nomeAeroporto,
                                    fkPais);
                        }
                    } else {
                        return String.format("('%s', %d, NULL)",
                                nomeAeroporto,
                                fkPais);
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (valores.isEmpty()) {
            return "";
        }

        sql.append(String.join(", ", valores));
        sql.append(";");

        return sql.toString();
    }

//    public static String ConstruirSqlInsertViagens(List<VooAnac> viagensExtraidas) {
//        if (viagensExtraidas.isEmpty()) {
//            return "";
//        }
//
//        StringBuilder sql = new StringBuilder("INSERT INTO Viagem (dtViagem, fkAeroportoOrigem, fkAeroportoDestino) VALUES ");
//
//        List<String> valores = viagensExtraidas.values().stream()
//                .map(estado -> String.format("(%s, '%s', '%s')",
//                        escaparString(String.valueOf(estado.getCodigoIbge())),
//                        escaparString(estado.getNome()),
//                        escaparString(estado.getRegiao()))
//                )
//                .collect(Collectors.toList());
//
//        sql.append(String.join(", ", valores));
//        sql.append(";");
//
//        return sql.toString();
//    }

    private static String escaparString(String input) {
        return input.replace("'", "''");
    }
}
