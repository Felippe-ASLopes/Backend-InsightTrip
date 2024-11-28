package Model;

import java.time.LocalDate;
import java.util.Date;

public class Crime {

    private String nome;
    private Integer ano;
    private Integer mes;
    private Integer estado;
    private Integer qtdOcorrencia;

    public Crime(String nome, Integer ano, Integer mes, Integer estado, Integer qtdOcorrencia) {
        this.nome = nome;
        this.ano = ano;
        this.mes = mes;
        this.estado = estado;
        this.qtdOcorrencia = qtdOcorrencia;
    }

    public String getNome() {
        return nome;
    }

    public Integer getAno() {
        return ano;
    }

    public Integer getMes() {
        return mes;
    }

    public Integer getEstado() {
        return estado;
    }

    public Integer getQtdOcorrencia() {
        return qtdOcorrencia;
    }
}
