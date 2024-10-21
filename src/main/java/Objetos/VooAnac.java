package Objetos;

public class VooAnac {

    private Integer ano;
    private Integer mes;
    private String aeroportoOrigem;
    private String ufAeroportoOrigem;
    private String regiaoAeroportoOrigem;
    private String paisOrigem;
    private String continentePaisOrigem;
    private String aeroportoDestino;
    private String ufAeroportoDestino;
    private String regiaoAeroportoDestino;
    private String paisDestino;
    private String continentePaisDestino = "AMÉRICA DO SUL";
    private Integer qtdPassageirosPagos;
    private Integer qtdPassageirosGratis;

    public VooAnac(Integer ano, Integer mes,
                   String aeroportoOrigem, String ufAeroportoOrigem, String regiaoAeroportoOrigem, String paisOrigem, String continentePaisOrigem,
                   String aeroportoDestino, String ufAeroportoDestino, String regiaoAeroportoDestino, String paisDestino,
                   Integer qtdPassageirosPagos, Integer qtdPassageirosGratis) {
        this.ano = ano;
        this.mes = mes;
        this.aeroportoOrigem = aeroportoOrigem;
        this.ufAeroportoOrigem = ufAeroportoOrigem;
        this.regiaoAeroportoOrigem = regiaoAeroportoOrigem;
        this.paisOrigem = paisOrigem;
        this.continentePaisOrigem = continentePaisOrigem;
        this.aeroportoDestino = aeroportoDestino;
        this.ufAeroportoDestino = ufAeroportoDestino;
        this.regiaoAeroportoDestino = regiaoAeroportoDestino;
        this.paisDestino = paisDestino;
        this.qtdPassageirosPagos = qtdPassageirosPagos;
        this.qtdPassageirosGratis = qtdPassageirosGratis;
    }

    public Integer getAno() {
        return ano;
    }

    public Integer getMes() {
        return mes;
    }

    public String getAeroportoOrigem() {
        return aeroportoOrigem;
    }

    public String getUfAeroportoOrigem() {
        return ufAeroportoOrigem;
    }

    public String getRegiaoAeroportoOrigem() {
        return regiaoAeroportoOrigem;
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }

    public String getContinentePaisOrigem() {
        return continentePaisOrigem;
    }

    public String getAeroportoDestino() {
        return aeroportoDestino;
    }

    public String getUfAeroportoDestino() {
        return ufAeroportoDestino;
    }

    public String getRegiaoAeroportoDestino() {
        return regiaoAeroportoDestino;
    }

    public String getPaisDestino() {
        return paisDestino;
    }

    public String getContinentePaisDestino() {
        return continentePaisDestino;
    }

    public Integer getQtdPassageirosPagos() {
        return qtdPassageirosPagos;
    }

    public Integer getQtdPassageirosGratis() {
        return qtdPassageirosGratis;
    }

    @Override
    public String toString() {
        return "VooAnac{" +
                "ano=" + ano +
                ", mes=" + mes +
                ", aeroportoOrigem='" + aeroportoOrigem + '\'' +
                ", ufAeroportoOrigem='" + ufAeroportoOrigem + '\'' +
                ", regiaoAeroportoOrigem='" + regiaoAeroportoOrigem + '\'' +
                ", paisOrigem='" + paisOrigem + '\'' +
                ", continentePaisOrigem='" + continentePaisOrigem + '\'' +
                ", aeroportoDestino='" + aeroportoDestino + '\'' +
                ", ufAeroportoDestino='" + ufAeroportoDestino + '\'' +
                ", regiaoAeroportoDestino='" + regiaoAeroportoDestino + '\'' +
                ", paisDestino='" + paisDestino + '\'' +
                ", continentePaisDestino='" + continentePaisDestino + '\'' +
                ", qtdPassageirosPagos=" + qtdPassageirosPagos +
                ", qtdPassageirosGratis=" + qtdPassageirosGratis +
                '}';
    }
}
