package cl.dev.mmatush.moviesapp.service;

import cl.dev.mmatush.moviesapp.model.dto.MovieDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovieQueueListener {

    private final ScraperService scraperService;
    private final MovieService movieService;

    @RabbitListener(queues = "movieIdQueue")
    public void receiveMovieId(String movieId) {
        log.info("Recibiendo movie <id: {}>", movieId);
        MovieDto movie = scraperService.getMovieDetails(movieId);
        movieService.createMovie(movie);
    }

}
