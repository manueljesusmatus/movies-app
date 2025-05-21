package cl.dev.mmatush.moviesapp.service.impl;

import cl.dev.mmatush.moviesapp.configuration.property.XPathProperties;
import cl.dev.mmatush.moviesapp.exception.ScraperException;
import cl.dev.mmatush.moviesapp.model.dto.MovieDto;
import cl.dev.mmatush.moviesapp.service.HtmlScraper;
import cl.dev.mmatush.moviesapp.service.ScraperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScraperServiceImpl implements ScraperService {

    private final HtmlScraper htmlScraper;
    private final ModelMapper modelMapper;
    private final XPathProperties xPathProperties;

    @Override
    public MovieDto getMovieDetails(String movieId) {
        try {
            log.info("Obteniendo metadata de <movie: {}>", movieId);
            Map<String, Object> result = htmlScraper.extractData(xPathProperties.getUrl() + movieId.toLowerCase());
            log.debug("metadata <result: {}>", result);
            return modelMapper.map(result, MovieDto.class);
        } catch (Exception e) {
            throw new ScraperException("Error obteniendo detalle de pelicula", e);
        }
    }

}
