package Objetos;

import java.util.Date;


public class Evento {

    private int id;
    private String nome;
    private String descricao;
    private Date dataHora;

    //Construtor vazio
    public Evento() {}

    //Construtor
    public Evento(int id, String nome, String descricao, Date dataHora) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataHora = dataHora;
    }


    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }
}
