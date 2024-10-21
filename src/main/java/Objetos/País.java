package Objetos;

public class País {

    private String nome;
    private String continente;

    public País(String nome, String continente) {
        this.nome = nome;
        this.continente = continente;
    }

    public String getNome() {
        return nome;
    }

    public String getContinente() {
        return continente;
    }
}
