package cl.dev.mmatush.moviesapp.service;

import cl.dev.mmatush.moviesapp.model.document.Movie;
import cl.dev.mmatush.moviesapp.model.dto.MovieDto;
import cl.dev.mmatush.moviesapp.model.dto.VideoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    Movie readMovie(String id);

    List<Movie> readMoviesByCast(String actor);

    List<Movie> readMoviesByStudio(String studio);

    List<Movie> readMoviesByGenre(String genre);

    List<Movie> readAllMovies();

    Page<Movie> readAllMovies(Pageable pageable);

    Optional<Movie> createMovie(MovieDto movie);

    Optional<Movie> updateMovieIfExists(Movie movie);

    MovieDto getMovieDetails(String movieId);

    Optional<Movie> createVideoDetailsToMovie(String id, VideoDto videoDto);

}
