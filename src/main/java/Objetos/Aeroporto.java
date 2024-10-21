package Objetos;

public class Aeroporto {

    private String nome;
    private String continente;
    private Estado estado;

    public Aeroporto() {}

    public Aeroporto(String nome, String continente, Estado estado) {
        this.nome = nome;
        this.continente = continente;
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public String getContinente() {
        return continente;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
