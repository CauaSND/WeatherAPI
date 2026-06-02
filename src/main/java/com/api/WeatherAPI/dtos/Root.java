package com.api.WeatherAPI.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Root {
    @NotNull
    public Coord coord;
    public ArrayList<Weather> weather;
    public String base;
    @NotNull
    public Main main;
    public Integer visibility;
    public Wind wind;
    public Rain rain;
    public Clouds clouds;
    public Integer dt;
    public Sys sys;
    public Integer timezone;
    public Integer id;
    @NotNull
    public String name;
    public int cod;
}
