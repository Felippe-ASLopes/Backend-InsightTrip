package Model;

import java.time.LocalDate;
import java.util.Date;


public class Evento {

    private String nome;
    private LocalDate dataIncio;
    private LocalDate dataFim;
    private Integer estado;


    //Construtor vazio
    public Evento() {}

    public Evento(String nome, LocalDate dataIncio, LocalDate dataFim, Integer estado) {
        this.nome = nome;
        this.dataIncio = dataIncio;
        this.dataFim = dataFim;
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataIncio() {
        return dataIncio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public Integer getEstado() {
        return estado;
    }
}
