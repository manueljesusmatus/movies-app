package cl.dev.mmatush.moviesapp.repository;

import cl.dev.mmatush.moviesapp.model.document.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {

    List<Movie> findMovieByCastContainsIgnoreCase(List<String> cast);

    List<Movie> findMovieByStudioEqualsIgnoreCase(String studio);

    List<Movie> findMovieByGenresContainsIgnoreCase(String genre);

}
