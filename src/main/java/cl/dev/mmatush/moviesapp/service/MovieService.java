package cl.dev.mmatush.moviesapp.service;

import cl.dev.mmatush.moviesapp.model.document.Movie;
import cl.dev.mmatush.moviesapp.model.dto.MovieDto;
import cl.dev.mmatush.moviesapp.model.dto.MovieImageDto;
import cl.dev.mmatush.moviesapp.model.dto.VideoDto;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    Movie readMovie(String id);

    List<Movie> readMoviesByCast(String actor);

    List<Movie> readMoviesByStudio(String studio);

    List<Movie> readMoviesByGenre(String genre);

    List<Movie> readAllMovies();

    Optional<Movie> createMovie(MovieDto movie);

    Optional<Movie> updateMovieIfExists(Movie movie);

    Optional<Movie> updateMovieIfExists(MovieDto movie);

    MovieDto getMovieDetails(String movieId);

    Optional<Movie> createVideoDetailsToMovie(String id, VideoDto videoDto);

    Optional<Movie> createImageDetailsToMovie(String id, MovieImageDto imagesDto);

}
