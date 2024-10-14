package Objetos;

public class Estado {
    private int id;
    private String nome;
    private String regiao;
    private String codigoIbge;

    //Contrutor vazio para o Hibernate
    public Estado(){}


    //Construtor
    public Estado(int id, String nome, String regiao, String codigoIbge) {
        this.id = id;
        this.nome = nome;
        this.regiao = regiao;
        this.codigoIbge = codigoIbge;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getRegiao() {
        return regiao;
    }

    public String getCodigoIbge() {
        return codigoIbge;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public void setCodigoIbge(String codigoIbge) {
        this.codigoIbge = codigoIbge;
    }
}
