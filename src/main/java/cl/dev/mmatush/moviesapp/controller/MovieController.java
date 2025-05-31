package cl.dev.mmatush.moviesapp.controller;

import cl.dev.mmatush.moviesapp.model.document.Movie;
import cl.dev.mmatush.moviesapp.model.dto.MovieDto;
import cl.dev.mmatush.moviesapp.model.dto.MovieImageDto;
import cl.dev.mmatush.moviesapp.model.dto.RestResponsePage;
import cl.dev.mmatush.moviesapp.model.dto.VideoDto;
import cl.dev.mmatush.moviesapp.service.MovieService;
import cl.dev.mmatush.moviesapp.service.PaginatedMovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    //TODO: separar controlador
    private final MovieService movieService;
    private final PaginatedMovieService paginatedMovieService;

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable String id) {
        Movie movie = movieService.readMovie(id);
        return Objects.isNull(movie) ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Movie> postMovie(@RequestBody MovieDto movie) {
        return movieService.createMovie(movie)
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/cast/{actor}")
    public List<Movie> getMoviesByCast(@PathVariable String actor) {
        return movieService.readMoviesByCast(actor);
    }

    @GetMapping("/studio/{studio}")
    public List<Movie> getMoviesByStudio(@PathVariable String studio) {
        return movieService.readMoviesByStudio(studio);
    }

    @GetMapping("/genre/{genre}")
    public List<Movie> getMoviesByGenre(@PathVariable String genre) {
        return movieService.readMoviesByGenre(genre);
    }

    @GetMapping("")
    public List<Movie> getAllMovies() {
        return movieService.readAllMovies();
    }

    @GetMapping("/page")
    public RestResponsePage<Movie> getAllMovies(@RequestParam(defaultValue = "0") int pageNumber,
                                         @RequestParam(defaultValue = "10") int pageSize) {
        if (pageNumber < 0 || pageSize < 10 || pageSize > 20) {
            throw new IllegalArgumentException("pageNumber o pageSize poseen valores incorrectos");
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return new RestResponsePage<>(paginatedMovieService.readAllMovies(pageable));
    }

    @GetMapping("/data/{id}")
    public MovieDto getMovieDetails(@PathVariable String id) {
        return movieService.getMovieDetails(id);
    }

    @PostMapping("/data/video")
    public List<Movie> postVideoMovie(@RequestBody List<VideoDto> videos) {
        return videos.stream()
                .map(videoDto -> movieService.createVideoDetailsToMovie(videoDto.getId(), videoDto).orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }

    @PostMapping("/data/image")
    public List<Movie> postImageMovie(@RequestBody List<MovieImageDto> images) {
        return images.stream()
                .map(imagesDto -> movieService.createImageDetailsToMovie(imagesDto.getId(), imagesDto).orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }

}
