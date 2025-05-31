package cl.dev.mmatush.moviesapp.service;

import java.io.IOException;
import java.util.Map;

public interface ScraperService {

    Map<String, Object> extractData(String movieId) throws IOException;

}
