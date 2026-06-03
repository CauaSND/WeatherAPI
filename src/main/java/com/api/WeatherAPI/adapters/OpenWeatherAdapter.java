package com.api.WeatherAPI.adapters;

import com.api.WeatherAPI.dtos.Coord;
import com.api.WeatherAPI.dtos.Main;
import com.api.WeatherAPI.dtos.Root;
import com.api.WeatherAPI.dtos.placeDTOS.PlaceRoot;
import com.api.WeatherAPI.expection.LocationNotFoundException;
import com.api.WeatherAPI.expection.WeatherApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.Normalizer;


@Component
public class OpenWeatherAdapter implements WeatherGateway {
    final RestTemplate restTemplate;
    String apiKey = System.getenv("weatherkey");

    @Autowired
    public OpenWeatherAdapter (RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public Main getWeatherByCityAndState (String state, String city) {
        PlaceRoot[] citiesAvailable = getCoords(city);
        Coord coord = findTheRightCoord(citiesAvailable, state);

        String uri = "https://api.openweathermap.org/data/2.5/weather?lat=" + coord.lat() + "&lon=" + coord.lon() + "&units=metric&appid=" + apiKey;
        Root root = restTemplate.getForObject(uri, Root.class);

        if (root == null)
            throw new WeatherApiException("There is no value, API error");
        else
            return root.main;
    }

    public PlaceRoot[] getCoords (String city) {
        String uri = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=5&appid=" + apiKey;

        PlaceRoot[] citiesAvailable = restTemplate.getForObject(uri, PlaceRoot[].class);

        isJsonReturnedNullOrEmpty(citiesAvailable);
        return citiesAvailable;
    }

    private void isJsonReturnedNullOrEmpty (PlaceRoot[] placeRoots) {
        if (placeRoots == null || placeRoots.length == 0) {
            throw new WeatherApiException("There is no value, API error");
        }
    }


    Coord findTheRightCoord (PlaceRoot[] placesRoots, String state) {
        for (PlaceRoot place : placesRoots) {
            if (place.state == null || place.state.isEmpty())
                continue;
            String fixState = Normalizer.normalize(place.state, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
            if (fixState.replace(" ", "-").equals(state))
                return new Coord(place.lon, place.lat);
        }
        throw new LocationNotFoundException("Yours state or city may has another name, input a different name");
    }
}
