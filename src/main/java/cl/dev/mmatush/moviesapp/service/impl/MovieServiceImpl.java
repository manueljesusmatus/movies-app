package cl.dev.mmatush.moviesapp.service.impl;

import cl.dev.mmatush.moviesapp.exception.DataException;
import cl.dev.mmatush.moviesapp.exception.ScraperException;
import cl.dev.mmatush.moviesapp.model.document.Movie;
import cl.dev.mmatush.moviesapp.model.dto.MovieDto;
import cl.dev.mmatush.moviesapp.model.dto.MovieImageDto;
import cl.dev.mmatush.moviesapp.model.dto.VideoDto;
import cl.dev.mmatush.moviesapp.repository.MovieRepository;
import cl.dev.mmatush.moviesapp.service.MovieMapperService;
import cl.dev.mmatush.moviesapp.service.MovieService;
import cl.dev.mmatush.moviesapp.service.ScraperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = {"movies"})
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ScraperService scraperService;
    private final MovieMapperService movieMapperService;

    @Override
    public Movie readMovie(String id) {
        log.info("GET movie <id: {}>", id);
        try {
            if (StringUtils.isBlank(id))
                throw new DataException("Movie id es empty");
            return movieRepository.findById(id.toUpperCase())
                    .map(m -> {
                        log.debug("Movie recuperada <Movie: {}>", m);
                        return m;
                    }).orElse(null);
        } catch (Exception e) {
            throw new DataException("Error al recuperar registro de pelicula", e);
        }
    }

    @Override
    @Cacheable(value = "actor", key = "#actor", unless = "#result==null")
    public List<Movie> readMoviesByCast(String actor){
        log.info("GET movies by cast");
        try {
            if (StringUtils.isBlank(actor))
                throw new DataException("Cast id es empty");
            List<Movie> movieList = movieRepository.findMovieByCastContainsIgnoreCase(List.of(actor));
            log.debug("Movies recuperados <Actor: {}, Movie: {}>", actor, movieList.size());
            return movieList;
        } catch (Exception e) {
            throw new DataException("Error al recuperar peliculas por cast", e);
        }
    }

    @Override
    @Cacheable(value = "studio", key = "#studio", unless = "#result==null")
    public List<Movie> readMoviesByStudio(String studio){
        log.info("GET movies by studio");
        try {
            if (StringUtils.isBlank(studio))
                throw new DataException("Studio id es empty");
            List<Movie> movieList = movieRepository.findMovieByStudioEqualsIgnoreCase(studio);
            log.debug("Movies recuperados <Studio: {}, Movie: {}>", studio, movieList.size());
            return movieList;
        } catch (Exception e) {
            throw new DataException("Error al recuperar peliculas de studio", e);
        }
    }

    @Override
    @Cacheable(value = "genre", key = "#genre", unless = "#result==null")
    public List<Movie> readMoviesByGenre(String genre){
        log.info("GET movies by genre");
        try {
            if (StringUtils.isBlank(genre))
                throw new DataException("Genre id es empty");
            List<Movie> movieList = movieRepository.findMovieByGenresContainsIgnoreCase(genre);
            log.debug("Movies recuperados <Genre: {}, Movie: {}>", genre, movieList.size());
            return movieList;
        } catch (Exception e) {
            throw new DataException("Error al recuperar peliculas por genre", e);
        }
    }

    @Override
    public List<Movie> readAllMovies() {
        log.info("GET all movies");
        try {
            List<Movie> movies = movieRepository.findAll();
            log.debug("Movies recuperadas <Movies: Lista de {} registros>", movies.size());
            return movies;
        } catch (Exception e) {
            throw new DataException("Error al recuperar registro de peliculas", e);
        }
    }

    @Override
    public Optional<Movie> createMovie(MovieDto movieDto) {
        try {
            Movie movie = movieMapperService.toEntity(movieDto);
            log.info("SAVE movie <id: {}>", movie.getId());
            if (StringUtils.isBlank(movie.getId()))
                throw new DataException("Movie id es empty");
            Optional<Movie> movieOptional = movieRepository.findById(movie.getId().toUpperCase());
            if (movieOptional.isPresent()) {
                log.warn("Movie <id: {}> ya existe", movie.getId());
                return movieOptional;
            }
            Movie res = movieRepository.save(movie);
            log.debug("Movie creada <Movie: {}>", res);
            return Optional.of(res);
        } catch (Exception e) {
            throw new DataException("Error al guardar registro de pelicula", e);
        }
    }

    @Override
    public Optional<Movie> updateMovieIfExists(Movie movie) {
        log.info("UPDATE movie <id: {}>", movie.getId());
        try {
            if (StringUtils.isBlank(movie.getId()))
                throw new DataException("Movie id es empty");
            Optional<Movie> movieOptional = movieRepository.findById(movie.getId().toUpperCase());
            if (movieOptional.isEmpty()) {
                log.warn("Movie <id: {}> no existe", movie.getId());
                return Optional.empty();
            }
            return Optional.of(movieRepository.save(movie));
        } catch (Exception e) {
            throw new DataException("Error al actualizar registro de pelicula", e);
        }
    }

    @Override
    public Optional<Movie> updateMovieIfExists(MovieDto movie) {
        return updateMovieIfExists(movieMapperService.toEntity(movie));
    }

    @Override
    public MovieDto getMovieDetails(String movieId) {
        try {
            log.info("Obteniendo metadata de <movie: {}>", movieId);
            Map<String, Object> result = scraperService.extractData(movieId);
            log.debug("Data obtenida de la web <Movie: {}>", result);
            return movieMapperService.toDto(result);
        } catch (Exception e) {
            throw new ScraperException("Error obteniendo detalle de pelicula", e);
        }
    }

    @Override
    public Optional<Movie> createVideoDetailsToMovie(String id, VideoDto videoDto) {
        try {
            return movieRepository.findById(id).map(movie -> {
                log.info("Actualizando <movie: {}, video: {}>", id, videoDto);
                movie.setVideo(movieMapperService.toEntity(videoDto));
                return Optional.of(movieRepository.save(movie));
            }).orElseGet( () -> {
                log.info("Creando <movie: {}, video: {}>", id, videoDto);
                MovieDto movieDto = getMovieDetails(id);
                movieDto.setVideo(videoDto);
                return createMovie(movieDto);
            });
        } catch (Exception e) {
            throw new DataException("Error al guardar registro de video", e);
        }
    }

    @Override
    public Optional<Movie> createImageDetailsToMovie(String id, MovieImageDto imageDto) {
        try {
            return movieRepository.findById(id).map(movie -> {
                log.info("Actualizando <movie: {}, image: {}>", id, imageDto);
                movie.setImages(movieMapperService.toEntity(imageDto));
                return Optional.of(movieRepository.save(movie));
            }).orElseGet( () -> {
                log.info("Creando <movie: {}, image: {}>", id, imageDto);
                MovieDto movieDto = getMovieDetails(id);
                movieDto.setImages(imageDto);
                return createMovie(movieDto);
            });
        } catch (Exception e) {
            throw new DataException("Error al guardar registro de imagen", e);
        }
    }

}
