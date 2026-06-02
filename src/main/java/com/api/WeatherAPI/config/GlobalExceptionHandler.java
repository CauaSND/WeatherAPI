package com.api.WeatherAPI.config;

import com.api.WeatherAPI.expection.LocationNotFoundException;
import com.api.WeatherAPI.expection.WeatherApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler (value = LocationNotFoundException.class)
    public ResponseEntity<String> notFoundLocation (LocationNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler (value = WeatherApiException.class)
    public ResponseEntity<String> weatherApiException (WeatherApiException e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler (MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException (MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatusCode.valueOf(422)).body(e.getMessage());
    }

}
