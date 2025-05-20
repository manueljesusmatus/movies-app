package cl.dev.mmatush.moviesapp.controller;

import cl.dev.mmatush.moviesapp.model.dto.MovieDto;
import cl.dev.mmatush.moviesapp.service.MovieQueuePublisher;
import cl.dev.mmatush.moviesapp.service.ScraperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/util")
@RequiredArgsConstructor
public class UtilController {

    private final MovieQueuePublisher movieQueuePublisher;
    private final ScraperService scraperService;

    @PostMapping("/{id}")
    public void publishMovie(@PathVariable String id) {
        movieQueuePublisher.publish(id);
    }

    @GetMapping("/data/{id}")
    public MovieDto getMovieDetails(@PathVariable String id) {
        return scraperService.getMovieDetails(id);
    }

}
