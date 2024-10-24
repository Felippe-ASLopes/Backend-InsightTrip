package Utils;

public class FormaterUtils {
    public static String formatarNomes(String nomePais) {
        String[] palavras = nomePais.split(" ");
        for (int i = 0; i < palavras.length; i++) {
            palavras[i] = palavras[i].substring(0, 1).toUpperCase() + palavras[i].substring(1).toLowerCase();
        }
        return String.join(" ", palavras);
    }
}
