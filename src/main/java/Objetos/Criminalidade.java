package Objetos;

import java.util.Date;

public class Criminalidade {

    private int id;
    private Date dataHora;
    private Integer qtdVitimas;
    private Estado estado;

    //Construtor vazio para o Hibernate
    public Criminalidade() {
    }

    //Construtor
    public Criminalidade(int id, Date dataHora, Integer qtdVitimas, Estado estado) {
        this.id = id;
        this.dataHora = dataHora;
        this.qtdVitimas = qtdVitimas;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public Integer getQtdVitimas() {
        return qtdVitimas;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public void setQtdVitimas(Integer qtdVitimas) {
        this.qtdVitimas = qtdVitimas;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
