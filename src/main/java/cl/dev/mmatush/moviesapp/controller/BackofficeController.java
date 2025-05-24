package cl.dev.mmatush.moviesapp.controller;

import cl.dev.mmatush.moviesapp.model.Movie;
import cl.dev.mmatush.moviesapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/backoffice")
@RequiredArgsConstructor
@Slf4j
public class BackofficeController {

    private static final int PAGE_SIZE = 10;
    private static final int PAGE_OFFSET = 0;

    private static final String MOVIES_LIST = "moviesList";
    private static final String MOVIES_FOLLOWED_LIST = "moviesFollowed";
    private static final String MOVIES_AVAILABLE_LIST = "moviesAvailable";
    private static final String MOVIES_RECOMMENDED_LIST = "moviesRecommended";

    private static final String DIRECTORY_PAGE = "directory";
    private static final String MOVIE_PAGE = "movie";
    private static final String HOME_PAGE = "home";

    private static final String TITLE_ATTRIBUTE = "titlePage";
    private static final String MOVIE_ATTRIBUTE = "movie";

    private final MovieService movieService;

    @GetMapping("/home")
    public String showHome(Model model) {
        loadMovies(model);
        List<Movie> genreRecommendedList = movieService.readMoviesByGenre("Mature Woman");
        model.addAttribute("genreRecommended", "Mature Woman");
        model.addAttribute("genreRecommendedList", genreRecommendedList.subList(0, Math.min(5, genreRecommendedList.size())));

        List<Movie> studioRecommendedList = movieService.readMoviesByStudio("MADONNA");
        model.addAttribute("studioRecommended", "MADONNA");
        model.addAttribute("studioRecommendedList", studioRecommendedList.subList(0, Math.min(5, studioRecommendedList.size())));

        List<Movie> actorRecommendedList = movieService.readMoviesByCast("Akane Mitani");
        model.addAttribute("actorRecommended", "Akane Mitani");
        model.addAttribute("actorRecommendedList", actorRecommendedList.subList(0, Math.min(5, actorRecommendedList.size())));
        return HOME_PAGE;
    }

    @GetMapping("/movie/{id}")
    public String showMovie(@PathVariable String id, Model model) {
        loadMovies(model);
        model.addAttribute(MOVIE_ATTRIBUTE, movieService.readMovie(id));
        return MOVIE_PAGE;
    }

    @GetMapping("/directory")
    public String showDirectory(@RequestParam(defaultValue = "0") int pageNumber, Model model) {
        loadMovies(model);
        model.addAttribute(MOVIES_LIST, movieService.readAllMovies(PageRequest.of(pageNumber, (int) (PAGE_SIZE * 2.5))));
        model.addAttribute(TITLE_ATTRIBUTE, "Directorio");
        return DIRECTORY_PAGE;
    }

    @GetMapping("/cast/{actor}")
    public String showCastMovie(@PathVariable String actor, Model model) {
        loadMovies(model);
        model.addAttribute(MOVIES_LIST, movieService.readMoviesByCast(actor));
        model.addAttribute(TITLE_ATTRIBUTE, "Todo de " + actor);
        return DIRECTORY_PAGE;
    }

    @GetMapping("/studio/{studio}")
    public String showStudioMovie(@PathVariable String studio, Model model) {
        loadMovies(model);
        model.addAttribute(MOVIES_LIST, movieService.readMoviesByStudio(studio));
        model.addAttribute(TITLE_ATTRIBUTE, "Estudio " + studio);
        return DIRECTORY_PAGE;
    }

    @GetMapping("/genre/{genre}")
    public String showGenreMovie(@PathVariable String genre, Model model) {
        loadMovies(model);
        model.addAttribute(MOVIES_LIST, movieService.readMoviesByGenre(genre));
        model.addAttribute(TITLE_ATTRIBUTE, "Explorar " + genre);
        return DIRECTORY_PAGE;
    }

    private void loadMovies(Model model) {
        model.addAttribute(MOVIES_FOLLOWED_LIST, movieService.readAllMovies(PageRequest.of(PAGE_OFFSET, PAGE_SIZE)));
        model.addAttribute(MOVIES_AVAILABLE_LIST, movieService.readAllMovies(PageRequest.of(PAGE_OFFSET + 1, PAGE_SIZE)));
        model.addAttribute(MOVIES_RECOMMENDED_LIST, movieService.readAllMovies(PageRequest.of(((PAGE_SIZE * 2) / 5) + 1, 5)));
    }

}
