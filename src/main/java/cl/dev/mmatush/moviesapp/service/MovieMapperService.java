package cl.dev.mmatush.moviesapp.service;

import cl.dev.mmatush.moviesapp.model.document.Movie;
import cl.dev.mmatush.moviesapp.model.document.Video;
import cl.dev.mmatush.moviesapp.model.dto.MovieDto;
import cl.dev.mmatush.moviesapp.model.dto.VideoDto;

import java.util.Map;

public interface MovieMapperService {

    Movie toEntity(MovieDto movieDto);

    MovieDto toDto(Movie movie);

    MovieDto toDto(Map<String, Object> objectMap);

    Video toEntity(VideoDto videoDto);

    VideoDto toDto(Video video);

}
