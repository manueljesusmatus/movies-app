package cl.dev.mmatush.moviesapp.controller;

import cl.dev.mmatush.moviesapp.exception.DataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.error("Error NoHandlerFoundException", ex);
        return generateError(HttpStatus.NOT_FOUND, "Error interno");
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Object handleBadCredentialsException(BadCredentialsException ex) {
        log.error("Error BadCredentialsException", ex);
        return generateError(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Error IllegalArgumentException", ex);
        return generateError(HttpStatus.BAD_REQUEST, "Argumento invalido");
    }

    @ExceptionHandler(DataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object handleDataException(DataException ex) {
        log.error("Error DataException", ex);
        return generateError(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno");
    }

    private Object generateError(HttpStatus status, String message) {
        return Map.of("error", status, "message", message);
    }

}

