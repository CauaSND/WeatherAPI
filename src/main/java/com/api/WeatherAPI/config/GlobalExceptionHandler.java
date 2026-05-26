package com.api.WeatherAPI.config;

import com.api.WeatherAPI.expection.LocationNotFoundException;
import com.api.WeatherAPI.expection.WeatherApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = LocationNotFoundException.class)
    public ResponseEntity<String> notFoundLocation(LocationNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(value = WeatherApiException.class)
    public ResponseEntity<String> weatherApiException (WeatherApiException e){
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

}
