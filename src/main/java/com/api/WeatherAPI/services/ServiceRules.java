package com.api.WeatherAPI.services;

import com.api.WeatherAPI.dtos.Main;

public interface ServiceRules {
    Main getWeatherByCityAndState(String state,String city);
}
