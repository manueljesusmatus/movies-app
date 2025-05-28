package cl.dev.mmatush.moviesapp.service;

import cl.dev.mmatush.moviesapp.model.document.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaginatedMovieService {

    Page<Movie> readMoviesByCast(String actor, Pageable pageable);

    Page<Movie> readMoviesByStudio(String studio, Pageable pageable);

    Page<Movie> readMoviesByGenre(String genre, Pageable pageable);

    Page<Movie> readMoviesByFavorite(Pageable pageable);

    Page<Movie> readMoviesUnrated(Pageable pageable);

    Page<Movie> readAllMovies(Pageable pageable);

    Page<Movie> readMoviesRecommended(Pageable pageable, Movie movie);
}
