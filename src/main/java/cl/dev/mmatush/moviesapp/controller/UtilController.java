package cl.dev.mmatush.moviesapp.controller;

import cl.dev.mmatush.moviesapp.service.event.MovieQueuePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/util")
@RequiredArgsConstructor
public class UtilController {

    private final MovieQueuePublisher movieQueuePublisher;

    @PostMapping("/{id}")
    public void publishMovie(@PathVariable String id) {
        movieQueuePublisher.publish(id);
    }

}
