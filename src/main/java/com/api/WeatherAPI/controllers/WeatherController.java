package com.api.WeatherAPI.controllers;

import com.api.WeatherAPI.dtos.Main;
import com.api.WeatherAPI.services.ServiceRules;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    ServiceRules service;

    public WeatherController(ServiceRules service){
        this.service = service;
    }

    @GetMapping("/current/{state}/{city}")
    public ResponseEntity<Main> getCurrentWeather(@PathVariable("state") String state, @PathVariable("city") String city) {
        Main mainData = service.getWeatherByCityAndState(state,city);
        return ResponseEntity.ok().body(mainData);
    }
}
