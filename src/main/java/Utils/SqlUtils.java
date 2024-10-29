package Utils;

import Objetos.AeroportoInfo;
import Objetos.Estado;
import Objetos.VooAnac;

import java.util.*;
import java.util.stream.Collectors;

public class SqlUtils {
    public static String ConstruirSqlInsertPais(Map<String, String> paisesEContinentes) {
        if (paisesEContinentes.isEmpty()) {
            return "";
        }

        StringBuilder sql = new StringBuilder("INSERT INTO Pais (Nome, Continente) VALUES ");

        List<String> valores = paisesEContinentes.entrySet().stream()
                .map(entry -> String.format("('%s', '%s')",
                        escaparString(entry.getKey()),
                        escaparString(entry.getValue())))
                .collect(Collectors.toList());

        sql.append(String.join(", ", valores));
        sql.append(";");

        return sql.toString();
    }

    public static String ConstruirSqlInsertEstado(Map<String, Estado> estados) {
        if (estados.isEmpty()) {
            return "";
        }

        StringBuilder sql = new StringBuilder("INSERT INTO UF (CodigoIBGE, Nome, Regiao) VALUES ");

        List<String> valores = estados.values().stream()
                .map(estado -> String.format("(%s, '%s', '%s')",
                        escaparString(String.valueOf(estado.getCodigoIbge())),
                        escaparString(estado.getNome()),
                        escaparString(estado.getRegiao()))
                )
                .collect(Collectors.toList());

        sql.append(String.join(", ", valores));
        sql.append(";");

        return sql.toString();
    }

    public static String ConstruirSqlInsertAeroporto(
            List<VooAnac> viagensExtraidas,
            Map<String, String> paisesEContinentesUnicos,
            Map<String, Estado> EstadosRegioes
    ) {
        if (viagensExtraidas.isEmpty() || paisesEContinentesUnicos.isEmpty()) {
            return "";
        }

        // 1. Atribuir IDs aos países com base na ordem do mapa (assumindo ordem como LinkedHashMap)
        Map<String, Integer> paisToId = new LinkedHashMap<>();
        int id = 1;
        for (String pais : paisesEContinentesUnicos.keySet()) {
            paisToId.put(pais, id++);
        }

        // 2. Coletar aeroportos únicos com informações de país e estado
        Map<String, AeroportoInfo> aeroportosOrigem = viagensExtraidas.stream()
                .filter(voo -> voo.getAeroportoOrigem() != null && !voo.getAeroportoOrigem().isEmpty())
                .collect(Collectors.toMap(
                        VooAnac::getAeroportoOrigem,
                        voo -> new AeroportoInfo(voo.getPaisOrigem(), voo.getUfAeroportoOrigem()),
                        (aeroportoExistente, aeroportoNovo) -> aeroportoExistente
                ));

        if (aeroportosOrigem.isEmpty()) {
            return "";
        }

        StringBuilder sql = new StringBuilder("INSERT INTO Aeroporto (NomeAeroporto, fkPais, fkEstado) VALUES ");

        List<String> valores = aeroportosOrigem.entrySet().stream()
                .map(entry -> {
                    String nomeAeroporto = escaparString(entry.getKey());
                    String pais = entry.getValue().getPaisOrigem();
                    Integer fkPais = paisToId.get(pais);

                    if (fkPais == null) {
                        return null;
                    }

                    if ("Brasil".equalsIgnoreCase(pais)) {
                        String uf = entry.getValue().getUfOrigem();
                        Estado estado = EstadosRegioes.get(uf);
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

    private static String escaparString(String input) {
        return input.replace("'", "''");
    }
}
