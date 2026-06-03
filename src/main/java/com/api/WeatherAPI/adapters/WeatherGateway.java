package com.api.WeatherAPI.adapters;

import com.api.WeatherAPI.dtos.Main;

public interface WeatherGateway {
    Main getWeatherByCityAndState (String state, String local);
}
