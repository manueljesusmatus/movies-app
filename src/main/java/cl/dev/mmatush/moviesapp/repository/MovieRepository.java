package cl.dev.mmatush.moviesapp.repository;

import cl.dev.mmatush.moviesapp.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
}
