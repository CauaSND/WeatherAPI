package com.api.WeatherAPI.services;

import com.api.WeatherAPI.adapters.WeatherCache;
import com.api.WeatherAPI.adapters.WeatherGateway;
import com.api.WeatherAPI.dtos.Main;
import com.api.WeatherAPI.dtos.placeDTOS.PlaceRoot;
import org.springframework.stereotype.Service;


@Service
public class ServiceOpenWeatherAPI implements ServiceRules {
    WeatherGateway weatherGateway;
    WeatherCache weatherCache;

    public ServiceOpenWeatherAPI (WeatherGateway weatherGateway, WeatherCache weatherCache) {
        this.weatherGateway = weatherGateway;
        this.weatherCache = weatherCache;
    }

    @Override
    public Main getWeatherByCityAndState (String state, String city) {
        Main cacheMain = weatherCache.get(city);
        if (cacheMain == null) {
            Main weatherMain = weatherGateway.getWeatherByCityAndState(state, city);
            weatherCache.put(city, weatherMain);
            return weatherMain;
        } else {
            return cacheMain;
        }
    }

    @Override
    public PlaceRoot[] getCities (String city) {
        PlaceRoot[] placeRootsCache = weatherCache.getCities(city);
        if (placeRootsCache == null) {
            PlaceRoot[] placeRoots = weatherGateway.getCities(city);
            weatherCache.putCities(city, placeRoots);
            return placeRoots;
        } else {
            return placeRootsCache;
        }
    }
}
