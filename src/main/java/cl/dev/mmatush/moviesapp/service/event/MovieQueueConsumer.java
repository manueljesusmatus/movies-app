package cl.dev.mmatush.moviesapp.service.event;

import cl.dev.mmatush.moviesapp.exception.DataException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovieQueueConsumer {

    private static final String MOVIE_QUEUE_NAME = "movies-queue";

    private final RabbitTemplate rabbitTemplate;

    public String consume() {
        try {
            log.info("Consumiendo mensajes de queue");
            Object message = rabbitTemplate.receiveAndConvert(MOVIE_QUEUE_NAME);
            return message != null ? message.toString() : "No hay mensajes";
        } catch (Exception e) {
            throw new DataException("Error al consumir movie queue", e);
        }
    }

}
