package Model;

public class Aeroporto {

    private Integer id;
    private String nomeAeroporto;
    private Integer fkPais;
    private Integer fkEstado;

    public Aeroporto(Integer id, String nomeAeroporto, Integer fkPais, Integer fkEstado) {
        this.id = id;
        this.nomeAeroporto = nomeAeroporto;
        this.fkPais = fkPais;
        this.fkEstado = fkEstado;
    }

    public Integer getId() {
        return id;
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