package com.api.WeatherAPI.adapters;

import com.api.WeatherAPI.dtos.Coord;
import com.api.WeatherAPI.dtos.Root;
import com.api.WeatherAPI.dtos.placeDTOS.PlaceRoot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith (MockitoExtension.class)
class OpenWeatherAdapterTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    OpenWeatherAdapter openWeatherAdapter;

    PlaceRoot[] placeRoots;
    Root rootTest;

    @BeforeEach
    void setup () {
        placeRoots = new PlaceRoot[]{PlaceRoot.builder().name("Osasco").lat(-17000.000).lon(18000.00).state("São Paulo").build(), PlaceRoot.builder().name("Osasco").lat(-45000.00).lon(-16000.00).state("Parana").build()};
    }

    @Test
    void ShouldGetCoordSuccessfully () {
        when(restTemplate.getForObject(any(String.class), eq(PlaceRoot[].class))).thenReturn(placeRoots);

        //
        final Coord coord = openWeatherAdapter.getCoord("sao-paulo", "Osasco");

        //
        assertNotNull(coord);
        assertEquals(-17000.000, coord.lat());
        assertEquals(18000.00, coord.lon());
    }
}