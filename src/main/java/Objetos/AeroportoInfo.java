package Objetos;

public class AeroportoInfo {
    private String paisOrigem;
    private String ufOrigem;

    public AeroportoInfo(String paisOrigem, String ufOrigem) {
        this.paisOrigem = paisOrigem;
        this.ufOrigem = ufOrigem;
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }

    public String getUfOrigem() {
        return ufOrigem;
    }
}
