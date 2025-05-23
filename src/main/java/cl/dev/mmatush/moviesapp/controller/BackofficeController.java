package cl.dev.mmatush.moviesapp.controller;

import cl.dev.mmatush.moviesapp.model.dto.UserMessage;
import cl.dev.mmatush.moviesapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/backoffice")
@RequiredArgsConstructor
@Slf4j
public class BackofficeController {

    private final MovieService movieService;

    public List<UserMessage> chatMessages = new ArrayList<>();

    @GetMapping("/movie/{id}")
    public String showMovie(@PathVariable String id, Model model) {
        model.addAttribute("moviesFollowed", movieService.readAllMovies(PageRequest.of(0, 20)));
        model.addAttribute("moviesAvailable", movieService.readAllMovies(PageRequest.of(1, 20)));
        model.addAttribute("moviesRecommended", movieService.readAllMovies(PageRequest.of(8, 5)));
        model.addAttribute("chatMessages", chatMessages);
        model.addAttribute("movie", movieService.readMovie(id));
        return "homev3";
    }

}
