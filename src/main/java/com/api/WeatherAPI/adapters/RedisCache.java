package com.api.WeatherAPI.adapters;

import com.api.WeatherAPI.dtos.Main;
import com.api.WeatherAPI.dtos.placeDTOS.PlaceRoot;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import redis.clients.jedis.RedisClient;


@Component
public class RedisCache implements WeatherCache {

    RedisClient redisClient;
    Gson gson;

    public RedisCache (RedisClient redisClient) {
        this.redisClient = redisClient;
        gson = new Gson();
    }

    @Override
    public Main put (String key, Main main) {

        //Convert object main to a json
        String jsonMain = gson.toJson(main);

        //Save the json object on redis, using a normal set
        redisClient.setex(key, 60 * 15, jsonMain);
        return main;
    }

    @Override
    public Main get (String key) {
        //Retrive the json, if saved return a value if not return a null
        String jsonFromServer = redisClient.get(key);
        if (jsonFromServer == null || jsonFromServer.isEmpty())
            return null;

        // json to main and return a main from this json
        return gson.fromJson(jsonFromServer, Main.class);
    }

    @Override
    public PlaceRoot[] putCities (String key, PlaceRoot[] placeRoots) {
        String jsonPlaceRoots = gson.toJson(placeRoots);
        if (jsonPlaceRoots == null || jsonPlaceRoots.isEmpty())
            return null;
        redisClient.setex(key + "Cities", 2592000L, jsonPlaceRoots);
        return placeRoots;
    }

    @Override
    public PlaceRoot[] getCities (String key) {
        String placeRootsJson = redisClient.get(key + "Cities");
        if (placeRootsJson == null || placeRootsJson.isEmpty())
            return null;
        return gson.fromJson(placeRootsJson, PlaceRoot[].class);
    }
}
