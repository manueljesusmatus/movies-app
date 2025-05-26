package cl.dev.mmatush.moviesapp.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovieQueuePublisher {

    private static final String MOVIE_QUEUE_NAME = "movies-queue";

    private final RabbitTemplate rabbitTemplate;

    public void publish(String movieId) {
        try {
            log.info("Publicando movie <id: {}>", movieId);
            rabbitTemplate.convertAndSend(MOVIE_QUEUE_NAME, movieId);
        } catch (Exception e) {
            log.error("Error publicando movie <id: {}>", movieId, e);
        }
    }

}
