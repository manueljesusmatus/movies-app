package cl.dev.mmatush.moviesapp.controller;

import cl.dev.mmatush.moviesapp.configuration.property.BackofficeProperties;
import cl.dev.mmatush.moviesapp.model.document.Movie;
import cl.dev.mmatush.moviesapp.model.dto.MovieDto;
import cl.dev.mmatush.moviesapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private static final String MOVIES_DOWNLOADED_LIST = "moviesDownloaded";
    private static final String MOVIES_FAILED_LIST = "moviesFailed";

    private static final String DIRECTORY_PAGE = "directory";
    private static final String FORMULARIO_PAGE = "formulario";
    private static final String MOVIE_PAGE = "movie";
    private static final String HOME_PAGE = "home";

    private static final String TITLE_ATTRIBUTE = "titlePage";
    private static final String MOVIE_ATTRIBUTE = "movie";

    private final MovieService movieService;
    private final BackofficeProperties properties;

    @GetMapping("/home")
    public String showHome(Model model) {
        loadMovies(model);
        List<Movie> genreRecommendedList = movieService.readMoviesByGenre(properties.getGenre());
        model.addAttribute("genreRecommended", properties.getGenre());
        model.addAttribute("genreRecommendedList", genreRecommendedList.subList(0, Math.min(5, genreRecommendedList.size())));

        List<Movie> studioRecommendedList = movieService.readMoviesByStudio(properties.getStudio());
        model.addAttribute("studioRecommended", properties.getStudio());
        model.addAttribute("studioRecommendedList", studioRecommendedList.subList(0, Math.min(5, studioRecommendedList.size())));

        List<Movie> actorRecommendedList = movieService.readMoviesByCast(properties.getActor());
        model.addAttribute("actorRecommended", properties.getActor());
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

    @GetMapping("/formulario")
    public String showFormulario(Model model) {
        List<Movie> moviesDownloaded = (List<Movie>) model.getAttribute(MOVIES_DOWNLOADED_LIST);
        List<String> moviesFailed = (List<String>) model.getAttribute(MOVIES_FAILED_LIST);
        loadMovies(model);
        model.addAttribute(TITLE_ATTRIBUTE, "Ingresar datos");
        model.addAttribute(MOVIES_DOWNLOADED_LIST, CollectionUtils.isEmpty(moviesDownloaded) ? new ArrayList<>() : moviesDownloaded);
        model.addAttribute(MOVIES_FAILED_LIST, CollectionUtils.isEmpty(moviesFailed) ? new ArrayList<>() : moviesFailed);
        return FORMULARIO_PAGE;
    }

    @PostMapping("/saveMovieList")
    public String saveMovieList(@ModelAttribute("moviesCreate") String moviesCreate, RedirectAttributes redirectAttributes) {
        List<Movie> moviesDownloaded = new ArrayList<>();
        List<String> moviesFailed = new ArrayList<>();
        List.of(moviesCreate.split(",")).forEach( movieId -> {
            try {
                downloadMovies(movieId, moviesDownloaded, moviesFailed);
            } catch (Exception e) {
                log.error("Error al guardar {}", movieId, e);
                moviesFailed.add(movieId);
            }
        });
        redirectAttributes.addFlashAttribute(MOVIES_DOWNLOADED_LIST, moviesDownloaded);
        redirectAttributes.addFlashAttribute(MOVIES_FAILED_LIST, moviesFailed);
        return "redirect:/backoffice/formulario";
    }

    @PostMapping("/saveMovie")
    public String saveMovie(@ModelAttribute("movie") MovieDto movie, RedirectAttributes redirectAttributes) {
        List<Movie> moviesDownloaded = new ArrayList<>();
        List<String> moviesFailed = new ArrayList<>();

        downloadMovies(movie.getId(), moviesDownloaded, moviesFailed);
        redirectAttributes.addFlashAttribute(MOVIES_DOWNLOADED_LIST, moviesDownloaded);
        redirectAttributes.addFlashAttribute(MOVIES_FAILED_LIST, moviesFailed);
        return "redirect:/backoffice/formulario";
    }

    private void loadMovies(Model model) {
        model.addAttribute(MOVIES_FOLLOWED_LIST, movieService.readAllMovies(PageRequest.of(PAGE_OFFSET, PAGE_SIZE)));
        model.addAttribute(MOVIES_AVAILABLE_LIST, movieService.readAllMovies(PageRequest.of(PAGE_OFFSET + 1, PAGE_SIZE)));
        model.addAttribute(MOVIES_RECOMMENDED_LIST, movieService.readAllMovies(PageRequest.of(((PAGE_SIZE * 2) / 5) + 1, 5)));
    }

    private void downloadMovies(String id, List<Movie> moviesDownloaded, List<String> moviesFailed) {
        try {
            Movie existingMovie = movieService.readMovie(id);
            if (Objects.nonNull(existingMovie)) {
                moviesDownloaded.add(existingMovie);
            } else {
                MovieDto result = movieService.getMovieDetails(id);
                movieService.createMovie(result).ifPresentOrElse(moviesDownloaded::add, () -> moviesFailed.add(id));
            }
        } catch (Exception e) {
            log.error("Error al guardar {}", id, e);
            moviesFailed.add(id);
        }
    }

}
