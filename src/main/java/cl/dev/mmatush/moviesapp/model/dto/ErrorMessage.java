package cl.dev.mmatush.moviesapp.model.dto;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ErrorMessage(String message, String status, int code, LocalDateTime timestamp) implements Serializable {

    public ErrorMessage(String message, HttpStatus status) {
        this(message, status.getReasonPhrase(), status.value(), LocalDateTime.now());
    }

}
