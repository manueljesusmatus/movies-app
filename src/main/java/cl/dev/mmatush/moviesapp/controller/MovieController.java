package cl.dev.mmatush.moviesapp.controller;

import cl.dev.mmatush.moviesapp.model.Movie;
import cl.dev.mmatush.moviesapp.model.dto.MovieDto;
import cl.dev.mmatush.moviesapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
@Slf4j
public class MovieController {

    private static final String TEMPLATE_LOG = "movie {}: {}";

    private final MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable String id) {
        return movieService.readMovie(id)
                .map(movie -> {
                    log.debug(TEMPLATE_LOG, HttpStatus.OK, movie);
                    return new ResponseEntity<>(movie, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.readAllMovies();
    }

    @GetMapping("/page")
    public Page<Movie> getAllMovies(@RequestParam(defaultValue = "0") int pageNumber,
                                    @RequestParam(defaultValue = "10") int pageSize) {
        if (pageNumber < 0 || pageSize < 10 || pageSize > 20) {
            throw new IllegalArgumentException("pageNumber o pageSize poseen valores incorrectos");
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return movieService.readAllMovies(pageable);
    }

    @PostMapping
    public ResponseEntity<Movie> postMovie(@RequestBody MovieDto movie) {
        return movieService.createMovie(movie)
                .map(result -> {
                    log.debug(TEMPLATE_LOG, HttpStatus.CREATED, result);
                    return new ResponseEntity<>(result, HttpStatus.CREATED);
                }).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

}
