package cl.dev.mmatush.moviesapp.service.impl;

import cl.dev.mmatush.moviesapp.configuration.property.XPathProperties;
import cl.dev.mmatush.moviesapp.exception.DataException;
import cl.dev.mmatush.moviesapp.exception.ScraperException;
import cl.dev.mmatush.moviesapp.model.Movie;
import cl.dev.mmatush.moviesapp.model.dto.MovieDto;
import cl.dev.mmatush.moviesapp.repository.MovieRepository;
import cl.dev.mmatush.moviesapp.service.MovieService;
import cl.dev.mmatush.moviesapp.service.ScraperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;
    private final ScraperService scraperService;
    private final XPathProperties xPathProperties;

    @Override
    public Movie readMovie(String id) {
        log.info("GET movie <id: {}>", id);
        try {
            if (!StringUtils.hasLength(id))
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
    public Page<Movie> readAllMovies(Pageable pageable) {
        log.info("GET all movies pageable <size: {}, number: {}>", pageable.getPageSize(), pageable.getPageNumber());
        try {
            Page<Movie> page = movieRepository.findAll(pageable);
            log.debug("Page recuperada <Page: {}>", page);
            return page;
        } catch (Exception e) {
            throw new DataException("Error al recuperar registro de peliculas", e);
        }
    }

    @Override
    public Optional<Movie> createMovie(MovieDto movieDto) {
        try {
            Movie movie = toEntity(movieDto);
            log.info("SAVE movie <id: {}>", movie.getId());
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
    public Movie toEntity(MovieDto movieDto) {
        return modelMapper.map(movieDto, Movie.class);
    }

    @Override
    public MovieDto toDto(Movie movie) {
        return modelMapper.map(movie, MovieDto.class);
    }

    @Override
    public MovieDto getMovieDetails(String movieId) {
        try {
            log.info("Obteniendo metadata de <movie: {}>", movieId);
            Map<String, Object> result = scraperService.extractData(xPathProperties.getUrl() + movieId.toLowerCase());
            log.debug("Data obtenida de la web <Movie: {}>", result);
            return modelMapper.map(result, MovieDto.class);
        } catch (Exception e) {
            throw new ScraperException("Error obteniendo detalle de pelicula", e);
        }
    }

}
