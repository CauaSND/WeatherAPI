package com.api.WeatherAPI.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Rain(@JsonProperty("1h") Double _1h) {
}
