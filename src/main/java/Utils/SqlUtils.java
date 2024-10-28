package Utils;

import Objetos.Estado;

import java.util.List;
import java.util.Map;
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

    private static String escaparString(String input) {
        return input.replace("'", "''");
    }
}
