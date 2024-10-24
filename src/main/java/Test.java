import ETL.LeitorVoos;
import Objetos.VooAnac;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        String nomeArquivo = "resumo_anual_2024.xlsx";
        Path caminho = Path.of(nomeArquivo);

        try (InputStream arquivo = Files.newInputStream(caminho)) {

            List<VooAnac> viagensExtraidas = LeitorVoos.ExtrairViagem(nomeArquivo, arquivo);

            Map<String, String> paisesEContinentesUnicos = viagensExtraidas.stream()
                    .filter(voo -> voo.getPaisOrigem() != null && !voo.getPaisOrigem().isEmpty())
                    .collect(Collectors.toMap(
                            VooAnac::getPaisOrigem,
                            VooAnac::getContinentePaisOrigem,
                            (continenteExistente, novoContinente) -> continenteExistente
                    ));

            Set<String> estadosOrigemUnicos = viagensExtraidas.stream()
                    .map(VooAnac::getUfAeroportoOrigem)
                    .filter(estado -> estado != null && !estado.isEmpty())
                    .collect(Collectors.toSet());

            paisesEContinentesUnicos.forEach((pais, continente) ->
                    System.out.println("País: " + pais + ", Continente: " + continente)
            );

            System.out.println("Estados de Destino Únicos:");
            estadosOrigemUnicos.forEach(System.out::println);
            System.out.println(estadosOrigemUnicos.size());


        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
