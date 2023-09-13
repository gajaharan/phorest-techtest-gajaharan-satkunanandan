package com.phorest.exception;

import com.phorest.client.model.MessageResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class PhorestExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PhorestDataNotFoundException.class)
    public MessageResponseDto handleDataNotFoundExceptions(PhorestDataNotFoundException ex) {
        log.warn(ex.getMessage());
        return new MessageResponseDto(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public MessageResponseDto handleRunTimeExceptionExceptions(RuntimeException ex) {
        log.error(String.format("Something went wrong: %s", ex.getMessage()));
        return new MessageResponseDto(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PhorestIOException.class)
    public MessageResponseDto handlePhorestIOExceptions(PhorestIOException ex) {
        log.error(String.format(ex.getMessage()));
        return new MessageResponseDto(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public MessageResponseDto handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn(String.format(ex.getMessage()));
        return new MessageResponseDto(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
