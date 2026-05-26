package com.api.WeatherAPI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class Config {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
