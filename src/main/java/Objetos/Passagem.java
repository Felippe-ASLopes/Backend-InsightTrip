package Objetos;

import java.time.YearMonth;

public class Passagem {

    private int id;
    private String nome;
    private String origem;
    private String destino;
    private YearMonth data;
    private Agencia agencia;


    public Passagem(){};


    public Passagem(int id, String nome, String origem, String destino, YearMonth data, Agencia agencia) {
        this.id = id;
        this.nome = nome;
        this.origem = origem;
        this.destino = destino;
        this.data = data;
        this.agencia = agencia;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public YearMonth getData() {
        return data;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setData(YearMonth data) {
        this.data = data;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }
}
