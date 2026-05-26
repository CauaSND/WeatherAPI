package com.api.WeatherAPI.services;

import com.api.WeatherAPI.adapters.WeatherGateway;
import com.api.WeatherAPI.dtos.Main;
import org.springframework.stereotype.Service;


@Service
public class ServiceOpenWeatherAPI implements ServiceRules {
    WeatherGateway weatherGateway;

    public ServiceOpenWeatherAPI(WeatherGateway weatherGateway){
        this.weatherGateway = weatherGateway;
    }

    @Override
    public Main getWeather (String state, String city) {
        return weatherGateway.getWeatherDetail(state,city);
    }
}
