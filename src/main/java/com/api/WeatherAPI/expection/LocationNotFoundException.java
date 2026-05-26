package com.api.WeatherAPI.expection;

public class LocationNotFoundException extends RuntimeException {
    public LocationNotFoundException (String message) {
        super(message);
    }
}
