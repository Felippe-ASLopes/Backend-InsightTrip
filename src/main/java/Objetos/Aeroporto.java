package Objetos;

public class Aeroporto {
    private String nomeAeroporto;
    private Integer fkPais;
    private Integer fkEstado;

    public Aeroporto(String nomeAeroporto, Integer fkPais, Integer fkEstado) {
        this.nomeAeroporto = nomeAeroporto;
        this.fkPais = fkPais;
        this.fkEstado = fkEstado;
    }

    public String getNomeAeroporto() {
        return nomeAeroporto;
    }

    public void setNomeAeroporto(String nomeAeroporto) {
        this.nomeAeroporto = nomeAeroporto;
    }

    public Integer getFkPais() {
        return fkPais;
    }

    public void setFkPais(Integer fkPais) {
        this.fkPais = fkPais;
    }

    public Integer getFkEstado() {
        return fkEstado;
    }

    public void setFkEstado(Integer fkEstado) {
        this.fkEstado = fkEstado;
    }
}