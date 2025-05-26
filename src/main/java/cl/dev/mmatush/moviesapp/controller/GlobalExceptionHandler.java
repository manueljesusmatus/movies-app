package cl.dev.mmatush.moviesapp.controller;

import cl.dev.mmatush.moviesapp.exception.DataException;
import cl.dev.mmatush.moviesapp.model.dto.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNoResourceFoundException(NoResourceFoundException ex) {
        log.error("Error NoResourceFoundException", ex);
        return generateError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Error HttpMessageNotReadableException", ex);
        return generateError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessage handleBadCredentialsException(BadCredentialsException ex) {
        log.error("Error BadCredentialsException", ex);
        return generateError(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Error IllegalArgumentException", ex);
        return generateError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleDataException(DataException ex) {
        log.error("Error DataException", ex);
        return generateError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private ErrorMessage generateError(HttpStatus status, String message) {
        return new ErrorMessage(message, status);
    }

}

