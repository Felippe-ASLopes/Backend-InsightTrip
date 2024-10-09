package Objetos;

public class VooExterior {

    private Integer idPaisOrigem;
    private Integer idEstadoDestino;
    private Integer ano;
    private Integer mes;
    private Integer qtdViagens;

    public VooExterior(Integer idPaisOrigem, Integer idEstadoDestino, Integer ano, Integer mes, Integer qtdViagens) {
        this.idPaisOrigem = idPaisOrigem;
        this.idEstadoDestino = idEstadoDestino;
        this.ano = ano;
        this.mes = mes;
        this.qtdViagens = qtdViagens;
    }

    public Integer getIdPaisOrigem() {
        return idPaisOrigem;
    }

    public Integer getIdEstadoDestino() {
        return idEstadoDestino;
    }

    public Integer getAno() {
        return ano;
    }

    public Integer getMes() {
        return mes;
    }

    public Integer getQtdViagens() {
        return qtdViagens;
    }

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

    @Override
    public String toString() {
        return String.format("""
                idPaisOrigem: %d
                idEstadoDestino: %d
                ano: %d
                mes: %d
                qtdViagens: %d
                """, idPaisOrigem, idEstadoDestino, ano, mes, qtdViagens);
    }
}