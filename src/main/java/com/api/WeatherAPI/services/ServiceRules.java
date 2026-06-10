package com.api.WeatherAPI.services;

import com.api.WeatherAPI.dtos.Main;
import com.api.WeatherAPI.dtos.placeDTOS.PlaceRoot;

public interface ServiceRules {
    Main getWeatherByCityAndState (String state, String city);

    PlaceRoot[] getCities (String city);
}
