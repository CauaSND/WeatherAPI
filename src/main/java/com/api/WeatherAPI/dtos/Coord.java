package com.api.WeatherAPI.dtos;

import jakarta.validation.constraints.NotNull;

public record Coord(@NotNull Double lon, @NotNull Double lat) {
}
