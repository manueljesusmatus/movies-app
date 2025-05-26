package cl.dev.mmatush.moviesapp.service.impl;

import cl.dev.mmatush.moviesapp.exception.DataException;
import cl.dev.mmatush.moviesapp.model.document.Movie;
import cl.dev.mmatush.moviesapp.model.document.Video;
import cl.dev.mmatush.moviesapp.model.dto.MovieDto;
import cl.dev.mmatush.moviesapp.model.dto.VideoDto;
import cl.dev.mmatush.moviesapp.service.MovieMapperService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MovieMapperServiceImpl implements MovieMapperService {

    private final ModelMapper modelMapper;

    @Override
    public Movie toEntity(MovieDto movieDto) {
        try {
            return modelMapper.map(movieDto, Movie.class);
        } catch (Exception e) {
            throw new DataException("Error al mapear Dto movie", e);
        }
    }

    @Override
    public MovieDto toDto(Movie movie) {
        try {
            return modelMapper.map(movie, MovieDto.class);
        } catch (Exception e) {
            throw new DataException("Error al mapear Document movie", e);
        }
    }

    @Override
    public MovieDto toDto(Map<String, Object> objectMap) {
        return modelMapper.map(objectMap, MovieDto.class);
    }

    @Override
    public Video toEntity(VideoDto videoDto) {
        try {
            return modelMapper.map(videoDto, Video.class);
        } catch (Exception e) {
            throw new DataException("Error al mapear Dto video", e);
        }
    }

    @Override
    public VideoDto toDto(Video video) {
        try {
            return modelMapper.map(video, VideoDto.class);
        } catch (Exception e) {
            throw new DataException("Error al mapear Document video", e);
        }
    }

}
