package cl.dev.mmatush.moviesapp.controller;

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

    private final MovieService movieService;

    @GetMapping("/movie/{id}")
    public String showMovie(@PathVariable String id, Model model) {
        loadMovies(model);
        model.addAttribute("movie", movieService.readMovie(id));
        return MOVIE_PAGE;
    }

    @GetMapping("/directory")
    public String showDirectory(@RequestParam(defaultValue = "0") int pageNumber, Model model) {
        loadMovies(model);
        model.addAttribute(MOVIES_LIST, movieService.readAllMovies(PageRequest.of(pageNumber, (int) (PAGE_SIZE * 2.5))));
        return DIRECTORY_PAGE;
    }

    @GetMapping("/cast/{actor}")
    public String showCastMovie(@PathVariable String actor, Model model) {
        loadMovies(model);
        model.addAttribute(MOVIES_LIST, movieService.readMoviesByCast(actor));
        return DIRECTORY_PAGE;
    }

    @GetMapping("/studio/{studio}")
    public String showStudioMovie(@PathVariable String studio, Model model) {
        loadMovies(model);
        model.addAttribute(MOVIES_LIST, movieService.readMoviesByStudio(studio));
        return DIRECTORY_PAGE;
    }

    @GetMapping("/genre/{genre}")
    public String showGenreMovie(@PathVariable String genre, Model model) {
        loadMovies(model);
        model.addAttribute(MOVIES_LIST, movieService.readMoviesByGenre(genre));
        return DIRECTORY_PAGE;
    }

    private void loadMovies(Model model) {
        model.addAttribute(MOVIES_FOLLOWED_LIST, movieService.readAllMovies(PageRequest.of(PAGE_OFFSET, PAGE_SIZE)));
        model.addAttribute(MOVIES_AVAILABLE_LIST, movieService.readAllMovies(PageRequest.of(PAGE_OFFSET + 1, PAGE_SIZE)));
        model.addAttribute(MOVIES_RECOMMENDED_LIST, movieService.readAllMovies(PageRequest.of(((PAGE_SIZE * 2) / 5) + 1, 5)));
    }

}
