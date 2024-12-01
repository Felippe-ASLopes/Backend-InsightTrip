package Utils;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Random;

public class FormaterDay {

    private static class SpecialDayConfig {
        private final int specialDay;
        private final double percentage;

        public SpecialDayConfig(int specialDay, double percentage) {
            this.specialDay = specialDay;
            this.percentage = percentage;
        }

        public int getSpecialDay() {
            return specialDay;
        }

        public double getPercentage() {
            return percentage;
        }
    }

    private static final Map<Integer, SpecialDayConfig> specialDayConfigs = new ConcurrentHashMap<>();

    private static final Random random = new Random();

    public static void configDia(int mes, int specialDay, double percentage) {
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("Mês deve estar entre 1 e 12");
        }
        if (specialDay < 1 || specialDay > 31) {
            throw new IllegalArgumentException("Dia especial inválido");
        }
        if (percentage < 0.0 || percentage > 1.0) {
            throw new IllegalArgumentException("Percentual deve estar entre 0.0 e 1.0");
        }
        specialDayConfigs.put(mes, new SpecialDayConfig(specialDay, percentage));
    }

    public static void formatarMes() {
        configDia(12, 25, 0.40);
        configDia(01, 1, 0.25);
        configDia(02, 13, 0.50);
        configDia(03, 1, 0.10);
        configDia(03, 15, 0.10);
        configDia(04, 17, 0.25);
        configDia(04, 17, 0.25);
        configDia(05, 8, 0.25);
        configDia(06, 12, 0.25);
        configDia(06, 24, 0.25);
        configDia(06, 28, 0.10);
        configDia(07, 26, 0.25);
        configDia(8, 11, 0.25);
        configDia(10, 12, 0.25);
        configDia(10, 31, 0.25);
        configDia(11, 29, 0.25);
    }

    public static LocalDate formatarDia(int ano, int mes, boolean specialDayRule) {
        if (ano < 0) {
            throw new IllegalArgumentException("Ano inválido");
        }
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("Mês deve estar entre 1 e 12");
        }

        YearMonth yearMonth = YearMonth.of(ano, mes);
        int maxDia = yearMonth.lengthOfMonth();

        int diaViagem;

        if (specialDayRule) {
            SpecialDayConfig config = specialDayConfigs.get(mes);
            if (config != null) {
                if (random.nextDouble() < config.getPercentage()) {
                    diaViagem = Math.min(config.getSpecialDay(), maxDia);
                } else {
                    diaViagem = gerarDiaAleatorio(maxDia);
                }
            } else {
                diaViagem = gerarDiaAleatorio(maxDia);
            }
        } else {
            diaViagem = gerarDiaAleatorio(maxDia);
        }

        return LocalDate.of(ano, mes, diaViagem);
    }

    private static int gerarDiaAleatorio(int maxDia) {
        return random.nextInt(maxDia) + 1;
    }
}