package com.anish.journalApp.service;

import com.anish.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Value("weather.api.key")
    private static String apiKey;

    private final static String url = "https://api.weatherstack.com/current";
//    private final static String url = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city){
//        String finalApi = url.replace("CITY" , city).replace("API_KEY" , apiKey);
        String finalApi = url + "?access_key=" + apiKey + "&query=" + city;
        ResponseEntity<WeatherResponse> response  = restTemplate.exchange(finalApi , HttpMethod.GET , null , WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }
}
