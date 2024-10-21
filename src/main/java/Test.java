import ETL.LeitorVoos;
import Objetos.VooAnac;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        String nomeArquivo = "resumo_anual_2024.xlsx";
        Path caminho = Path.of(nomeArquivo);

        try (InputStream arquivo = Files.newInputStream(caminho)) {

            List<VooAnac> viagensExtraidas = LeitorVoos.ExtrairViagem(nomeArquivo, arquivo);

            Set<String> uniquePaisOrigem = viagensExtraidas.stream()
                    .map(VooAnac::getPaisOrigem)
                    .filter(pais -> pais != null && !pais.isEmpty())
                    .collect(Collectors.toSet());

            System.out.println("Países de Origem Únicos:");
            uniquePaisOrigem.forEach(System.out::println);


        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
