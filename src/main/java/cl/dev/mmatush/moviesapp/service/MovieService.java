package cl.dev.mmatush.moviesapp.service;

import cl.dev.mmatush.moviesapp.model.Movie;
import cl.dev.mmatush.moviesapp.model.dto.MovieDto;
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

    Movie toEntity(MovieDto movieDto);

    MovieDto toDto(Movie movie);

    MovieDto getMovieDetails(String movieId);

}
