package com.api.WeatherAPI.adapters;

import com.api.WeatherAPI.dtos.Main;

public interface WeatherCache {
    Main put (Main main);

    Main get ();
}
