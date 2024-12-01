package Utils;

import Model.*;

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

    public static String ConstruirInsertViagem(List<VooAnac> voos, List<Aeroporto> aeroportos) {
        if (voos == null || voos.isEmpty()) {
            return "";
        }

        StringBuilder sql = new StringBuilder("INSERT INTO Viagem (dtViagem, fkAeroportoOrigem, fkAeroportoDestino, QtdPassageirosPagos, QtdPassageirosGratis) VALUES ");

        List<String> valores = new ArrayList<>();

        for (VooAnac voo : voos) {

            String dtViagem = voo.getDataViagem().toString();
            Integer qtdPassageirosPagos = voo.getQtdPassageirosPagos();
            Integer qtdPassageirosGratis = voo.getQtdPassageirosGratis();

            Integer fkAeroportoOrigem = null;
            for (Aeroporto aeroporto : aeroportos) {
                if (aeroporto.getNomeAeroporto().equalsIgnoreCase(voo.getAeroportoOrigem())) {
                    fkAeroportoOrigem = aeroporto.getId();
                    break;
                }
            }

            Integer fkAeroportoDestino = null;
            for (Aeroporto aeroporto : aeroportos) {
                if (aeroporto.getNomeAeroporto().equalsIgnoreCase(voo.getAeroportoDestino())) {
                    fkAeroportoDestino = aeroporto.getId();
                    break;
                }
            }

            if (fkAeroportoOrigem != null && fkAeroportoDestino != null) {
                valores.add(String.format("('%s', %d, %d, %d, %d)", dtViagem, fkAeroportoOrigem, fkAeroportoDestino, qtdPassageirosPagos, qtdPassageirosGratis));
            }
        }

        if (valores.isEmpty()) {
            return "";
        }

        sql.append(String.join(", ", valores));
        sql.append(";");

        return sql.toString();
    }
    public static String ConstruirInsertEventos(List<Evento> eventos) {
        if (eventos == null || eventos.isEmpty()) {
            return "";
        }

        StringBuilder sql = new StringBuilder("INSERT INTO Evento (Nome, DataInicio, DataFim) VALUES ");

        for (Evento evento : eventos) {
            sql.append(String.format("('%s', '%s', '%s')", escaparString(evento.getNome()), evento.getDataIncio(), evento.getDataFim()));

            if (eventos.indexOf(evento) < eventos.size() - 1){
                sql.append(",");
            }
            else {
                sql.append(";");
            }
        }

        return sql.toString();
    }

    public static String ConstruirInsertEventosEstados(List<Evento> eventos, List<Estado> estados) {
        if (eventos == null || eventos.isEmpty()) {
            return "";
        }

        StringBuilder sql = new StringBuilder("INSERT INTO EstadoHasEvento (fkEvento, fkEstado) VALUES ");

        boolean isFirstValue = true;

        for (Evento evento : eventos) {
            if (evento.getEstado() != 1) {
                if (!isFirstValue) {
                    sql.append(",");
                }
                sql.append(String.format("('%d', '%d')", eventos.indexOf(evento) + 1, evento.getEstado()));
                isFirstValue = false;
            } else {
                for (Estado estado : estados) {
                    if (!isFirstValue) {
                        sql.append(",");
                    }
                    sql.append(String.format("('%d', '%d')", eventos.indexOf(evento) + 1, estado.getCodigoIbge()));
                    isFirstValue = false;
                }
            }
        }
        sql.append(";");

        return sql.toString();
    }

    public static String ConstruirInsertCrimes(List<Crime> crimes) {
        if (crimes == null || crimes.isEmpty()) {
            return "";
        }

        StringBuilder sql = new StringBuilder("INSERT INTO Crime (Nome, qtdOcorrencia, Data, fkestado) VALUES ");


        for (Crime crime : crimes) {
            sql.append(String.format("('%s', '%d', '%s', '%d')", escaparString(crime.getNome()), crime.getQtdOcorrencia(), crime.getData(), crime.getEstado()));

            if (crimes.indexOf(crime) < crimes.size() - 1){
                sql.append(",");
            }
            else {
                sql.append(";");
            }
        }

        return sql.toString();
    }

    private static String escaparString(String input) {
        return input.replace("'", "''");
    }
}
