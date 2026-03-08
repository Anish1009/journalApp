package com.anish.journalApp.service;

import com.anish.journalApp.api.response.WeatherResponse;
import com.anish.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Autowired
    private AppCache appCache;

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city){
        String finalApi = appCache.getAppCache().get("weather_api").replace("<city>" , city).replace("<apiKey>" , apiKey);
        ResponseEntity<WeatherResponse> response  = restTemplate.exchange(finalApi , HttpMethod.GET , null , WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }
}
