package com.is.smartlight.services;

import com.is.smartlight.config.AuthClient;
import com.is.smartlight.models.Lightbulb;
import com.is.smartlight.repositories.LightGroupRepository;
import com.is.smartlight.repositories.LightbulbRepository;
import com.is.smartlight.repositories.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LightbulbService {
    private final LightbulbRepository lightbulbRepository;
    private final KeycloakAdminService keycloakAdminService;
    private final UserRepository userRepository;
    private final WeatherService weatherService;

    @Autowired
    LightbulbService(KeycloakAdminService keycloakAdminService, UserRepository userRepository, LightbulbRepository lightbulbRepository, WeatherService weatherService) {
        this.lightbulbRepository = lightbulbRepository;
        this.keycloakAdminService = keycloakAdminService;
        this.userRepository = userRepository;
        this.weatherService = weatherService;
    }
    @SneakyThrows
    public void setLightbulbIntensityByOutsideLuminosity(Long id, String city, int desiredLuminosity){
        Lightbulb lightbulb = lightbulbRepository.getById(id);
        if (desiredLuminosity == 0){
            lightbulb.setIntensityPercentage(0.0F);
            return;
        }

        String outsideLuminosityString = weatherService.getOutsideLuminosity(city);
        int intakeLuminosity = Integer.parseInt(outsideLuminosityString) / 25; //luminozitatea care intra pe fereastra (am aproximat suprafata incaperii la 25m^2,
                                                                            // fara legatura cu 25 din formula)
        if( intakeLuminosity < desiredLuminosity){
            int newLightbulbIntensity = desiredLuminosity * 25; //nr de metri^2
            int maxIntensity = lightbulb.getMaxIntensity();
            if (maxIntensity < newLightbulbIntensity){
                throw new Exception("Can't achieve desired luminosity");
            }
            lightbulb.setIntensityPercentage((float) (newLightbulbIntensity/desiredLuminosity));

        }

    }
}
