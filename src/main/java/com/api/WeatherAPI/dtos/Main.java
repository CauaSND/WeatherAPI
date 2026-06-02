package com.api.WeatherAPI.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Main {
    @NotNull
    public Double temp;
    @NotNull
    public Double feels_like;
    @NotNull
    public Double temp_min;
    @NotNull
    public Double temp_max;
    public Integer pressure;
    public Integer humidity;
    public Integer sea_level;
    public Integer grnd_level;
}
