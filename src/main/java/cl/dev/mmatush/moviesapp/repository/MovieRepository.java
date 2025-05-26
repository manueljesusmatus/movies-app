package cl.dev.mmatush.moviesapp.repository;

import cl.dev.mmatush.moviesapp.model.document.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {

    List<Movie> findMovieByCastContainsIgnoreCase(List<String> cast);

    Page<Movie> findMovieByCastContainsIgnoreCase(List<String> cast, Pageable pageable);

    List<Movie> findMovieByStudioEqualsIgnoreCase(String studio);

    Page<Movie> findMovieByStudioEqualsIgnoreCase(String studio, Pageable pageable);

    List<Movie> findMovieByGenresContainsIgnoreCase(String genre);

    Page<Movie> findMovieByGenresContainsIgnoreCase(String genre, Pageable pageable);

}
