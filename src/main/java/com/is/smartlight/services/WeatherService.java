package com.is.smartlight.services;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.weather.Weather;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private final OpenWeatherMapClient openWeatherMapClient;

    @Autowired
    public WeatherService(){
        this.openWeatherMapClient = new OpenWeatherMapClient("2b169e4873efa67ac2c9ec4d5dfab1f4");
    }

    @SneakyThrows
    public String getOutsideLuminosity(String city){
        final Weather weather = this.openWeatherMapClient
                .currentWeather()
                .single()
                .byCityName(city)
                .language(Language.ENGLISH)
                .unitSystem(UnitSystem.STANDARD)
                .retrieve()
                .asJava();
        String outsideLuminosityInLumens;
        if(weather.getWeatherState().getIconId().equals("01d")){
            outsideLuminosityInLumens = "10000 lux";
        } else if(weather.getWeatherState().getIconId().equals("02d")){
            outsideLuminosityInLumens = "5000 lux";
        } else if(weather.getWeatherState().getIconId().equals("03d")){
            outsideLuminosityInLumens = "2000 lux";
        } else if(weather.getWeatherState().getIconId().equals("04d")){
            outsideLuminosityInLumens = "1000 lux";
        } else if(weather.getWeatherState().getIconId().equals("09d")){
            outsideLuminosityInLumens = "750 lux";
        } else if(weather.getWeatherState().getIconId().equals("10d")){
            outsideLuminosityInLumens = "2000 lux";
        } else if(weather.getWeatherState().getIconId().equals("11d")){
            outsideLuminosityInLumens = "100 lux";
        } else if(weather.getWeatherState().getIconId().equals("13d")){
            outsideLuminosityInLumens = "2000 lux";
        } else if(weather.getWeatherState().getIconId().equals("50d")){
            outsideLuminosityInLumens = "200 lux";
        } else if(weather.getWeatherState().getIconId().equals("01n")){
            outsideLuminosityInLumens = "0.1 lux";
        } else{
            outsideLuminosityInLumens = "0.01 lux";
        }
        return outsideLuminosityInLumens;
    }

}
