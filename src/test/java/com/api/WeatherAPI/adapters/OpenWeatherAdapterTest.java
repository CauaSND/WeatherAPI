package com.api.WeatherAPI.adapters;

import com.api.WeatherAPI.dtos.Coord;
import com.api.WeatherAPI.dtos.Main;
import com.api.WeatherAPI.dtos.Root;
import com.api.WeatherAPI.dtos.placeDTOS.PlaceRoot;
import com.api.WeatherAPI.expection.LocationNotFoundException;
import com.api.WeatherAPI.expection.WeatherApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
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
    String city = "Osasco";
    String state = "sao-paulo";

    @BeforeEach
    void setup () {
        placeRoots = new PlaceRoot[]{PlaceRoot.builder().name("Osasco").lat(-17000.000).lon(18000.00).state("São Paulo").build(), PlaceRoot.builder().name("Osasco").lat(-45000.00).lon(-16000.00).state("Parana").build()};
        rootTest = Root.builder()
                .main(
                        Main.builder()
                                .feels_like(17.00)
                                .temp(18.00)
                                .build()
                )
                .build();
    }

    @Test
    void ShouldGetWeatherDetailSuccessfully () {
        //Given
        when(restTemplate.getForObject(any(String.class), eq(PlaceRoot[].class))).thenReturn(placeRoots);
        when(restTemplate.getForObject(any(String.class), eq(Root.class))).thenReturn(rootTest);
        //when
        Main main = openWeatherAdapter.getWeatherByCityAndState("sao-paulo", "Osasco");
        //Then
        assertNotNull(main);
    }

    @Test
    void ShouldGetWeatherDetailFailure () {
        //Given
        when(restTemplate.getForObject(any(String.class), eq(PlaceRoot[].class))).thenReturn(placeRoots);
        when(restTemplate.getForObject(any(String.class), eq(Root.class))).thenReturn(null);
        //when
        WeatherApiException weatherApiException = assertThrows(WeatherApiException.class, () ->
                openWeatherAdapter.getWeatherByCityAndState("sao-paulo", "osasco"));
        //Then

        assertEquals("There is no value, API error", weatherApiException.getMessage());

    }

    @Test
    void ShouldGetCoordSuccessfully () {
        //Given
        when(restTemplate.getForObject(any(String.class), eq(PlaceRoot[].class))).thenReturn(placeRoots);

        //When
        final PlaceRoot[] citiesAvailable = openWeatherAdapter.getCities("Osasco");

        //Then
        assertNotNull(citiesAvailable);
    }

    @Test
    void ShouldFilterPlacesRootsSuccessfully () {

        //when
        Coord filtedCoord = openWeatherAdapter.findTheRightCoord(placeRoots, this.state);

        //then
        assertNotNull(filtedCoord);
        assertEquals(-17000.000, filtedCoord.lat());
        assertEquals(18000.00, filtedCoord.lon());
    }

    @Test
    void ShouldFilterPlacesRootsFailure () {

        //when
        LocationNotFoundException locationNotFoundException = assertThrows(LocationNotFoundException.class,
                () -> openWeatherAdapter.findTheRightCoord(placeRoots, "ceara"));

        //then
        assertEquals("Yours state or city may has another name, input a different name", locationNotFoundException.getMessage());
    }
}