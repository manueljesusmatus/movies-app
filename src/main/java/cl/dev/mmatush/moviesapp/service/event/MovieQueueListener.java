package cl.dev.mmatush.moviesapp.service.event;

import cl.dev.mmatush.moviesapp.model.dto.MovieDto;
import cl.dev.mmatush.moviesapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovieQueueListener {

    private final MovieService movieService;

    @RabbitListener(queues = "movieIdQueue")
    public void receiveMovieId(String movieId) {
        try {
            log.info("Recibiendo movie <id: {}>", movieId);
            MovieDto movie = movieService.getMovieDetails(movieId);
            movieService.createMovie(movie);
        } catch (Exception e) {
            log.error("Error al recibir movie <id: {}>", movieId, e);
        }

    }

}
