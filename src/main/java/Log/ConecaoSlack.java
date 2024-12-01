package Log;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import static Log.Log.*;

public class ConecaoSlack {
    private static final Logger logger = LoggerFactory.getLogger(ConecaoSlack.class);

    public static void enviarMensagemSlack() {
        Slack slack = Slack.getInstance();

        String mensagem = "Bem-vindo ao time Insight Trip!";
        Dotenv dotenv = Dotenv.load();

        String idCanal = dotenv.get("CANAL");
        String token = dotenv.get("TOKEN");

        if (token == null || idCanal == null) {
            logger.error("{}Erro: Variáveis de ambiente TOKEN ou CANAL não encontradas!{}", LOG_COLOR_RED, LOG_COLOR_RESET);
            return;
        }

        try {
            ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
                    .channel(idCanal).text(mensagem)
            );

            if (response.isOk()) {
                logger.info("{}Mensagem enviada com sucesso! {}", LOG_COLOR_GREEN, LOG_COLOR_RESET);
            } else {
                logger.error("{}Erro ao enviar mensagem: {} {}", LOG_COLOR_RED, response.getError(), LOG_COLOR_RESET);
            }
        } catch (IOException | SlackApiException e) {
            e.printStackTrace();
        }
    }
}
