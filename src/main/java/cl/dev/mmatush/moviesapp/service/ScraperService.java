package cl.dev.mmatush.moviesapp.service;

import cl.dev.mmatush.moviesapp.model.dto.MovieDto;

public interface ScraperService {

    MovieDto getMovieDetails(String movieId);

}
