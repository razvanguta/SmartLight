package com.is.smartlight.controllers;


import com.is.smartlight.services.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.is.smartlight.utility.HttpStatusUtility.successResponse;

@RestController
@Tag(name = "Weather Controller", description = "Set of endpoints for Retrieving the weather condition.")
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Operation(summary = "Get outside luminosity.")
    @GetMapping("/get-luminosity")
    public String getOutsideLuminosity(@RequestParam String city) {
        return weatherService.getOutsideLuminosity(city);
    }

}
