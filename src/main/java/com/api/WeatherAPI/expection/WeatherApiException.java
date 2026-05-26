package com.api.WeatherAPI.expection;

public class WeatherApiException extends RuntimeException{
    public WeatherApiException (String message) {
        super(message);
    }

}
