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
    public String converterToStringMes() {
        return switch (this.mes) {
            case 1 -> "janeiro";
            case 2 -> "fevereiro";
            case 3 -> "março";
            case 4 -> "abril";
            case 5 -> "maio";
            case 6 -> "junho";
            case 7 -> "julho";
            case 8 -> "agosto";
            case 9 -> "setembro";
            case 10 -> "outubro";
            case 11 -> "novembro";
            case 12 -> "dezembro";
            default -> "";
        };
    }

    public String converterToStringPaisOrigem() {
        return switch (this.idPaisOrigem) {
            case 1 -> "África do Sul";
            case 2 -> "Angola";
            case 3 -> "Cabo Verde";
            case 4 -> "Egito";
            case 5 -> "Gana";
            case 6 -> "Marrocos";
            case 7 -> "Moçambique";
            case 8 -> "Nigéria";
            case 9 -> "Quênia";
            case 10 -> "Tunísia";
            case 11 -> "Outros Países";
            case 12 -> "Costa Rica";
            case 13 -> "Cuba";
            case 14 -> "El Salvador";
            case 15 -> "Guatemala";
            case 16 -> "Haiti";
            case 17 -> "Honduras";
            case 18 -> "Nicarágua";
            case 19 -> "Panamá";
            case 20 -> "República Dominicana";
            case 21 -> "Trinidad e Tobago";
            case 22 -> "Outros Países";
            case 23 -> "Canadá";
            case 24 -> "Estados Unidos";
            case 25 -> "México";
            case 26 -> "Argentina";
            case 27 -> "Bolívia";
            case 28 -> "Chile";
            case 29 -> "Colômbia";
            case 30 -> "Equador";
            case 31 -> "Guiana";
            case 32 -> "Guiana Francesa";
            case 33 -> "Paraguai";
            case 34 -> "Peru";
            case 35 -> "Suriname";
            case 36 -> "Uruguai";
            case 37 -> "Venezuela";
            case 38 -> "Arábia Saudita";
            case 39 -> "Bangladesh";
            case 40 -> "China";
            case 41 -> "China";
            case 42 -> "Cingapura";
            case 43 -> "Filipinas";
            case 44 -> "Índia";
            case 45 -> "Indonésia";
            case 46 -> "Irã";
            case 47 -> "Israel";
            case 48 -> "Japão";
            case 49 -> "Líbano";
            case 50 -> "Malásia";
            case 51 -> "Paquistão";
            case 52 -> "República da Coreia";
            case 53 -> "Síria";
            case 54 -> "Tailândia";
            case 55 -> "Taiwan";
            case 56 -> "Outros Países";
            case 57 -> "Alemanha";
            case 58 -> "Áustria";
            case 59 -> "Bélgica";
            case 60 -> "Bulgária";
            case 61 -> "Croácia";
            case 62 -> "Dinamarca";
            case 63 -> "Eslováquia";
            case 64 -> "Eslovênia";
            case 65 -> "Espanha";
            case 66 -> "Estônia";
            case 67 -> "Finlândia";
            case 68 -> "França";
            case 69 -> "Grécia";
            case 70 -> "Holanda";
            case 71 -> "Hungria";
            case 72 -> "Irlanda";
            case 73 -> "Itália";
            case 74 -> "Letônia";
            case 75 -> "Lituânia";
            case 76 -> "Luxemburgo";
            case 77 -> "Noruega";
            case 78 -> "Polônia";
            case 79 -> "Portugal";
            case 80 -> "Inglaterra";
            case 81 -> "República Tcheca";
            case 82 -> "Romênia";
            case 83 -> "Rússia";
            case 84 -> "Sérvia";
            case 85 -> "Suécia";
            case 86 -> "Suíça";
            case 87 -> "Turquia";
            case 88 -> "Ucrânia";
            case 89 -> "Outros Países";
            case 90 -> "Austrália";
            case 91 -> "Nova Zelândia";
            case 92 -> "Outros Países";
            case 93 -> "Países não especificados";
            default -> "País desconhecido";
        };
    }
    public String converterToStringEstado() {
        return switch (this.idEstadoDestino) {
            case 1 -> "Acre";
            case 2 -> "Alagoas";
            case 3 -> "Amapá";
            case 4 -> "Amazonas";
            case 5 -> "Bahia";
            case 6 -> "Ceará";
            case 7 -> "Distrito Federal";
            case 8 -> "Espírito Santo";
            case 9 -> "Goiás";
            case 10 -> "Maranhão";
            case 11 -> "Mato Grosso";
            case 12 -> "Mato Grosso do Sul";
            case 13 -> "Minas Gerais";
            case 14 -> "Pará";
            case 15 -> "Paraíba";
            case 16 -> "Paraná";
            case 17 -> "Pernambuco";
            case 18 -> "Piauí";
            case 19 -> "Rio de Janeiro";
            case 20 -> "Rio Grande do Norte";
            case 21 -> "Rio Grande do Sul";
            case 22 -> "Rondônia";
            case 23 -> "Roraima";
            case 24 -> "Santa Catarina";
            case 25 -> "São Paulo";
            case 26 -> "Sergipe";
            case 27 -> "Tocantins";
            default -> "Estado desconhecido";
        };
    }
    public String createName(){
        return String.format("%s-%s-%d", this.converterToStringPaisOrigem(), this.converterToStringEstado(), this.getAno());
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