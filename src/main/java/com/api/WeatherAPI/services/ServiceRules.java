package com.api.WeatherAPI.services;

import com.api.WeatherAPI.dtos.Main;

public interface ServiceRules {
    Main getWeather(String state,String city);
}
