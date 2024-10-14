package Objetos;

public class Aeroporto {

    private int id;
    private String nome;
    private String sigla;
    private String continente;
    private Estado estado;

    public Aeroporto() {}

    public Aeroporto(int id, String nome, String sigla, String continente, Estado estado) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
        this.continente = continente;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }

    public String getContinente() {
        return continente;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
