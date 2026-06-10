package com.api.WeatherAPI.adapters;

import com.api.WeatherAPI.dtos.Main;
import com.api.WeatherAPI.dtos.placeDTOS.PlaceRoot;

public interface WeatherGateway {
    Main getWeatherByCityAndState (String state, String local);

    PlaceRoot[] getCities (String city);
}
