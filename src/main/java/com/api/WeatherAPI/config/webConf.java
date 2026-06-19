package com.api.WeatherAPI.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class webConf implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers (ResourceHandlerRegistry registry) {
        // Serve standard static resources from /static/
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        // Serve image assets placed under src/main/resources/imgs at runtime via /imgs/**
        registry.addResourceHandler("/imgs/**")
                .addResourceLocations("classpath:/imgs/");
    }

}
