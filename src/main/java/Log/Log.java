package Log;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {

    private static final Logger logger = Logger.getLogger(Log.class.getName());

    public static final String LOG_COLOR_RESET = "\u001B[0m";
    public static final String LOG_COLOR_RED = "\u001B[31m";
    public static final String LOG_COLOR_GREEN = "\u001B[32m";
    public static final String LOG_COLOR_YELLOW = "\u001B[33m";

    public static void main(String[] args) {
        try {

            DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new CustomFormatter());

            logger.addHandler(consoleHandler);
            logger.setUseParentHandlers(false);

            logger.info("Iniciando a página em: " + LocalDateTime.now().format(dateTimeFormat));
            logger.info("Iniciação completa em: " + LocalDateTime.now().format(dateTimeFormat));
            logger.info("Fazendo login em: " + LocalDateTime.now().format(dateTimeFormat));
            logger.info("Login completo em: " + LocalDateTime.now().format(dateTimeFormat));
            logger.severe("Erro ao fazer login em: " + LocalDateTime.now().format(dateTimeFormat));
            logger.info("Verificando sistema em: " + LocalDateTime.now().format(dateTimeFormat));
            logger.info("Iniciando conexão com o banco em: " + LocalDateTime.now().format(dateTimeFormat));
            logger.severe("Erro ao conectar com o banco em: " + LocalDateTime.now().format(dateTimeFormat));
            logger.info("Conexão com o banco realizada com sucesso em: " + LocalDateTime.now().format(dateTimeFormat));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class CustomFormatter extends Formatter {

        @Override
        public String format(LogRecord record) {
            StringBuilder sb = new StringBuilder();

            if (record.getLevel().intValue() >= Level.SEVERE.intValue()) {
                sb.append(LOG_COLOR_RED);
            } else if (record.getLevel().intValue() >= Level.INFO.intValue()) {
                sb.append(LOG_COLOR_GREEN);
            } else {
                sb.append(LOG_COLOR_YELLOW);
            }

            sb.append(formatMessage(record));
            sb.append(LOG_COLOR_RESET).append("\n");

            return sb.toString();
        }
    }
}
