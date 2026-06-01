package com.api.WeatherAPI.dtos.placeDTOS;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceRoot {
    public String name;
    public LocalNames local_names;
    public double lat;
    public double lon;
    public String country;
    public String state;
}
