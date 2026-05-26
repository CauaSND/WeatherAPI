package com.api.WeatherAPI.adapters;

import com.api.WeatherAPI.dtos.Main;

public interface WeatherGateway {
    Main getWeatherDetail(String state, String local);
}
