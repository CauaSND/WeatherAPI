package com.api.WeatherAPI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.RedisClient;

@Configuration
@EnableWebMvc
public class Config {
    @Bean
    public RestTemplate restTemplate () {
        return new RestTemplate();
    }

    @Bean
    public RedisClient redisClient () {
        String redisURL = System.getenv("redisURL");
        String redisPORT = System.getenv("redisPORT");

        String password = System.getenv("redisPassword");

        String host = (redisURL != null) ? redisURL : "localhost";
        int port = (redisPORT != null) ? Integer.getInteger(redisPORT) : 6379;

        JedisClientConfig config = DefaultJedisClientConfig.builder()
                .user("default")
                .password(password)
                .build();
        return new RedisClient.Builder().hostAndPort(host, port)
                .clientConfig(config)
                .build();
    }
}
