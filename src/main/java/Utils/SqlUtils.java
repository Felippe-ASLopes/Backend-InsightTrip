package Utils;

import Model.Aeroporto;
import Model.Estado;
import Model.Pais;

import java.util.*;
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

        return sql.toString();
    }

    public static String ConstruirInsertAeroporto(List<Aeroporto> aeroportos) {
        if (aeroportos == null || aeroportos.isEmpty()) {
            return "";
        }

        StringBuilder sql = new StringBuilder("INSERT INTO Aeroporto (NomeAeroporto, fkPais, fkEstado) VALUES ");

        List<String> valores = aeroportos.stream()
                .map(aeroporto -> String.format("('%s', %d, %s)",
                        escaparString(aeroporto.getNomeAeroporto()),
                        aeroporto.getFkPais(),
                        aeroporto.getFkEstado() != null ? aeroporto.getFkEstado().toString() : "NULL"
                ))
                .collect(Collectors.toList());

        sql.append(String.join(", ", valores));

        return sql.toString();
    }

    private static String escaparString(String input) {
        return input.replace("'", "''");
    }
}