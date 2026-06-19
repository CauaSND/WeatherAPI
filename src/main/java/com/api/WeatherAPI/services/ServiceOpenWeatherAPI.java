package com.api.WeatherAPI.services;

import com.api.WeatherAPI.adapters.WeatherCache;
import com.api.WeatherAPI.adapters.WeatherGateway;
import com.api.WeatherAPI.dtos.Main;
import com.api.WeatherAPI.dtos.placeDTOS.PlaceRoot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class ServiceOpenWeatherAPI implements ServiceRules {
    WeatherGateway weatherGateway;
    WeatherCache weatherCache;
    Boolean isUsingFictionalData;

    public ServiceOpenWeatherAPI (WeatherGateway weatherGateway, WeatherCache weatherCache) {
        this.weatherGateway = weatherGateway;
        this.weatherCache = weatherCache;
        this.isUsingFictionalData = System.getenv("weatherkey") == null || System.getenv("weatherkey").isEmpty();
    }

    @Override
    public Main getWeatherByCityAndState (String state, String city) {
        if (getFictionalMain() != null) {
            return getFictionalMain();
        }

        return getMainFromCacheOrApi(city, state);
    }


    @Override
    public PlaceRoot[] getCities (String city) {
        if (getFictionalCities() != null) {
            return getFictionalCities();
        }

        return getCitiesFromCacheOrApi(city);
    }

    //Logica para cache, primeiro tenta pegar do cache, se não tiver, pega da API e salva no cache

    private Main getMainFromCacheOrApi (String city, String state) {
        Main cacheMain = weatherCache.get(city);
        if (cacheMain == null) {
            Main weatherMain = weatherGateway.getWeatherByCityAndState(state, city);
            weatherCache.put(city, weatherMain);
            return weatherMain;
        } else {
            return cacheMain;
        }
    }

    private PlaceRoot[] getCitiesFromCacheOrApi (String city) {
        PlaceRoot[] placeRootsCache = weatherCache.getCities(city);
        if (placeRootsCache != null) {
            return placeRootsCache;
        } else {
            PlaceRoot[] placeRoots = weatherGateway.getCities(city);
            weatherCache.putCities(city, placeRoots);
            return placeRoots;
        }
    }

    private Main getFictionalMain () {
        if (isUsingFictionalData) {
            return Main.builder()
                    .temp(12.0)
                    .temp_max(14.0)
                    .temp_min(10.0)
                    .feels_like(11.0)
                    .grnd_level(1000)
                    .humidity(80)
                    .sea_level(1010)
                    .build();
        }
        return null;
    }

    private PlaceRoot[] getFictionalCities () {
        if (isUsingFictionalData) {
            ArrayList<PlaceRoot> placeRoots = new ArrayList<>();
            for (char letter = 'A'; letter <= 'Z'; letter++) {
                placeRoots.add(PlaceRoot.builder()
                        .name(letter + "City")
                        .state(letter + "State")
                        .country(letter + "Country")
                        .build());
            }
            return placeRoots.toArray(new PlaceRoot[0]);
        }
        return null;
    }


}
