package Model;

import java.time.LocalDate;
import java.util.Date;

public class Crime {

    private String nome;
    private LocalDate data;
    private Integer estado;
    private Integer qtdOcorrencia;

    public Crime(String nome, LocalDate data, Integer estado, Integer qtdOcorrencia) {
        this.nome = nome;
        this.data = data;
        this.estado = estado;
        this.qtdOcorrencia = qtdOcorrencia;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getData() {return data;}

    public Integer getEstado() {
        return estado;
    }

    public Integer getQtdOcorrencia() {
        return qtdOcorrencia;
    }
}
