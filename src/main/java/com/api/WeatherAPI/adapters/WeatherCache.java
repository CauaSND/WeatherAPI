package com.api.WeatherAPI.adapters;

import com.api.WeatherAPI.dtos.Main;

public interface WeatherCache {
    Main put (String key, Main main);

    Main get (String key);
}
