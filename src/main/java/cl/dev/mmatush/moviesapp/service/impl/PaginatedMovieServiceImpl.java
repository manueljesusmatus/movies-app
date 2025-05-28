package cl.dev.mmatush.moviesapp.service.impl;

import cl.dev.mmatush.moviesapp.exception.DataException;
import cl.dev.mmatush.moviesapp.model.document.Movie;
import cl.dev.mmatush.moviesapp.repository.MovieRepository;
import cl.dev.mmatush.moviesapp.service.PaginatedMovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaginatedMovieServiceImpl implements PaginatedMovieService {

    private final MovieRepository movieRepository;

    @Override
    public Page<Movie> readMoviesByCast(String actor, Pageable pageable) {
        log.info("GET movies pageable by cast");
        try {
            if (StringUtils.isBlank(actor))
                throw new DataException("Cast id es empty");
            Page<Movie> moviePages = movieRepository.findMovieByCastContainsIgnoreCase(List.of(actor), pageable);
            log.debug("Movies recuperados <Actor: {}, Page: {}>", actor, moviePages.getTotalElements());
            return moviePages;
        } catch (Exception e) {
            throw new DataException("Error al recuperar peliculas por cast", e);
        }
    }

    @Override
    public Page<Movie> readMoviesByStudio(String studio, Pageable pageable) {
        log.info("GET movies pageable by studio");
        try {
            if (StringUtils.isBlank(studio))
                throw new DataException("Studio id es empty");
            Page<Movie> moviePages = movieRepository.findMovieByStudioEqualsIgnoreCase(studio, pageable);
            log.debug("Movies recuperados <Studio: {}, Page: {}>", studio, moviePages.getTotalElements());
            return moviePages;
        } catch (Exception e) {
            throw new DataException("Error al recuperar peliculas de studio", e);
        }
    }

    @Override
    public Page<Movie> readMoviesByGenre(String genre, Pageable pageable) {
        log.info("GET movies pageable by genre");
        try {
            if (StringUtils.isBlank(genre))
                throw new DataException("Genre id es empty");
            Page<Movie> moviePages = movieRepository.findMovieByGenresContainsIgnoreCase(genre, pageable);
            log.debug("Movies recuperados <Genre: {}, Page: {}>", genre, moviePages.getTotalElements());
            return moviePages;
        } catch (Exception e) {
            throw new DataException("Error al recuperar peliculas por genre", e);
        }
    }

    @Override
    public Page<Movie> readMoviesByFavorite(Pageable pageable) {
        log.info("GET movies pageable by favorite");
        try {
            Page<Movie> moviePage = movieRepository.findMovieByFavorite(true, pageable);
            log.debug("Movies recuperados <Favorite> {}", moviePage.getTotalElements());
            return moviePage;
        } catch (Exception e) {
            throw new DataException("Error al recuperar peliculas por favorite", e);
        }
    }

    @Override
    public Page<Movie> readMoviesUnrated(Pageable pageable) {
        log.info("GET movies pageable pending");
        try {
            Page<Movie> moviePage = movieRepository.findMovieByRatingIsNull(pageable);
            log.debug("Movies recuperados <Pending> {}", moviePage.getTotalElements());
            return moviePage;
        } catch (Exception e) {
            throw new DataException("Error al recuperar peliculas por pending", e);
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
    public Page<Movie> readMoviesRecommended(Pageable pageable, Movie movie) {
        log.info("GET movies pageable recommended");
        try {
            long total = movieRepository.count();
            int randomSkip = (int) (Math.random() * Math.max(1, total - pageable.getPageSize()));
            List<Movie> movieList = movieRepository.findAll()
                    .stream().skip(randomSkip)
                    .limit(pageable.getPageSize())
                    .toList();
            return new PageImpl<>(movieList, pageable, total);
        } catch (Exception e) {
            throw new DataException("Error al recuperar peliculas por recommended", e);
        }
    }

}
