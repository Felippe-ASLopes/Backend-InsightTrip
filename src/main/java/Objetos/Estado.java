package Objetos;

public class Estado {

    private String nome;
    private String regiao;
    private País país;
    private Integer codigoIbge;

    //Contrutor vazio para o Hibernate
    public Estado(){}


    //Construtor
    public Estado(String nome, String regiao, País país) {
        this.nome = nome;
        this.regiao = regiao;
        this.país = país;
        this.codigoIbge = ConverterCodigoIBGE(regiao);
    }

    public String getNome() {
        return nome;
    }

    public String getRegiao() {
        return regiao;
    }

    public Integer getCodigoIbge() {
        return codigoIbge;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public void setCodigoIbge(Integer codigoIbge) {
        this.codigoIbge = codigoIbge;
    }

    public int ConverterCodigoIBGE(String uf) {
        return switch (uf) {
            case "Acre" -> 12;
            case "Alagoas" -> 27;
            case "Amapá" -> 16;
            case "Amazonas" -> 13;
            case "Bahia" -> 29;
            case "Ceará" -> 23;
            case "Distrito Federal" -> 53;
            case "Espírito Santo" -> 32;
            case "Goiás" -> 52;
            case "Maranhão" -> 21;
            case "Mato Grosso" -> 51;
            case "Mato Grosso do Sul" -> 50;
            case "Minas Gerais" -> 31;
            case "Pará" -> 15;
            case "Paraíba" -> 25;
            case "Paraná" -> 41;
            case "Pernambuco" -> 26;
            case "Piauí" -> 22;
            case "Rio Grande do Norte" -> 24;
            case "Rio Grande do Sul" -> 43;
            case "Rio de Janeiro" -> 33;
            case "Rondônia" -> 11;
            case "Roraima" -> 14;
            case "Santa Catarina" -> 42;
            case "São Paulo" -> 35;
            case "Sergipe" -> 28;
            case "Tocantins" -> 17;
            default -> -1;
        };
    }

    public static String ConverterNomeEstado(String uf) {
    return switch (uf) {
        case "AC" -> "Acre";
        case "AL" -> "Alagoas";
        case "AP" -> "Amapá";
        case "AM" -> "Amazonas";
        case "BA" -> "Bahia";
        case "CE" -> "Ceará";
        case "DF" -> "Distrito Federal";
        case "ES" -> "Espírito Santo";
        case "GO" -> "Goiás";
        case "MA" -> "Maranhão";
        case "MT" -> "Mato Grosso";
        case "MS" -> "Mato Grosso do Sul";
        case "MG" -> "Minas Gerais";
        case "PA" -> "Pará";
        case "PB" -> "Paraíba";
        case "PR" -> "Paraná";
        case "PE" -> "Pernambuco";
        case "PI" -> "Piauí";
        case "RN" -> "Rio Grande do Norte";
        case "RS" -> "Rio Grande do Sul";
        case "RJ" -> "Rio de Janeiro";
        case "RO" -> "Rondônia";
        case "RR" -> "Roraima";
        case "SC" -> "Santa Catarina";
        case "SP" -> "São Paulo";
        case "SE" -> "Sergipe";
        case "TO" -> "Tocantins";
        default -> "Estado desconhecido";
    };
}

}
