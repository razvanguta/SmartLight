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
            outsideLuminosityInLumens = "15000 lumens";
        } else if(weather.getWeatherState().getIconId().equals("02d")){
            outsideLuminosityInLumens = "12500 lumens";
        } else if(weather.getWeatherState().getIconId().equals("03d")){
            outsideLuminosityInLumens = "10500 lumens";
        } else if(weather.getWeatherState().getIconId().equals("04d")){
            outsideLuminosityInLumens = "8870 lumens";
        } else if(weather.getWeatherState().getIconId().equals("09d")){
            outsideLuminosityInLumens = "7000 lumens";
        } else if(weather.getWeatherState().getIconId().equals("10d")){
            outsideLuminosityInLumens = "9000 lumens";
        } else if(weather.getWeatherState().getIconId().equals("11d")){
            outsideLuminosityInLumens = "6000 lumens";
        } else if(weather.getWeatherState().getIconId().equals("13d")){
            outsideLuminosityInLumens = "7000 lumens";
        } else if(weather.getWeatherState().getIconId().equals("50d")){
            outsideLuminosityInLumens = "5500 lumens";
        } else if(weather.getWeatherState().getIconId().equals("01n")){
            outsideLuminosityInLumens = "1500 lumens";
        } else{
            outsideLuminosityInLumens = "500 lumens";
        }
        return outsideLuminosityInLumens;
    }

}
