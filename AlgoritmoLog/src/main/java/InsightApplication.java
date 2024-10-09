import ETL.Leitor;
import Objetos.VooExterior;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class InsightApplication {
    public static void main(String[] args) {
        String nomeArquivo = "chegadas_2023.xlsx";
        Path caminho = Path.of(nomeArquivo);

        try (InputStream arquivo = Files.newInputStream(caminho)) {

            List<VooExterior> voosExtraidos = Leitor.ExtrairVoo(nomeArquivo, arquivo);

            int numeroDeVoosParaExibir = Math.min(10, voosExtraidos.size());

            System.out.println("Exibindo os " + numeroDeVoosParaExibir + " primeiros voos:\n");

            for (int i = 0; i < numeroDeVoosParaExibir; i++) {
                VooExterior voo = voosExtraidos.get(i);
                System.out.println(voo.toString());
                System.out.println("----------------------------");
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}