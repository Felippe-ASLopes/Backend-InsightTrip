package Model;

public class Pais {
    private Integer id;
    private String nome;
    private String continente;

    public static String obterContinente(String pais) {
        return switch (pais) {
            case "Polônia", "Portugal", "Suíça", "Itália", "Inglaterra", "Luxemburgo", "Bélgica", "Holanda", "Alemanha",
                 "França", "Espanha" -> "Europa";
            case "Angola", "Nigéria", "Gana", "Etiópia", "Cabo Verde", "Senegal", "África Do Sul", "Marrocos",
                 "Costa Do Marfim", "Egito", "República Do Congo", "Togo", "Uganda", "Ilhas Canárias" -> "África";
            case "Turquia", "Azerbaijão", "Omã", "Qatar", "Kuwait", "Arábia Saudita", "Emirados Árabes", "Índia",
                 "Sri Lanka", "China", "Coreia Do Sul" -> "Ásia";
            case "México", "Estados Unidos", "Canadá" -> "América do Norte";
            case "Panamá", "Trinidad E Tobago", "Curaçao", "Martinica", "Porto Rico", "República Dominicana" ->
                    "América Central";
            case "Bolívia", "Equador", "Chile", "Argentina", "Colômbia", "Paraguai", "Peru", "Venezuela", "Uruguai",
                 "Guiana", "Suriname", "Guiana Francesa", "Brasil" -> "América do Sul";
            default -> "País não encontrado ou fora do escopo.";
        };
    }

    public Pais(Integer id, String nome, String continente) {
        this.id = id;
        this.nome = nome;
        this.continente = continente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getContinente() {
        return continente;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }

}