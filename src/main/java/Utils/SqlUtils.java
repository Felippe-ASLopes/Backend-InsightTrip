package Utils;

import java.util.List;
import java.util.Map;
import java.util.Set;
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

//    public static String ConstruirSqlInsertPais(Set<String> estados) {
//        if (estados.isEmpty()) {
//            return "";
//        }
//
//        StringBuilder sql = new StringBuilder("INSERT INTO  (Nome, Continente) VALUES ");
//
//        List<String> valores = paisesEContinentes.entrySet().stream()
//                .map(entry -> String.format("('%s', '%s')",
//                        escaparString(entry.getKey()),
//                        escaparString(entry.getValue())))
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
