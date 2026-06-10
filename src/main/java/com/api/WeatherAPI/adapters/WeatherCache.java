package com.api.WeatherAPI.adapters;

import com.api.WeatherAPI.dtos.Main;
import com.api.WeatherAPI.dtos.placeDTOS.PlaceRoot;

public interface WeatherCache {
    Main put (String key, Main main);

    Main get (String key);

    PlaceRoot[] putCities (String city, PlaceRoot[] placeRoots);

    PlaceRoot[] getCities (String city);
}
