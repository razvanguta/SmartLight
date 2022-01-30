package com.is.smartlight.controllers;


import com.is.smartlight.services.LightbulbService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Lightbulb Controller")
@RequestMapping("/lightbulbs")
public class LightbulbController {
    private final LightbulbService lightbulbService;

    @Autowired
    public LightbulbController(LightbulbService lightbulbService){
        this.lightbulbService = lightbulbService;
    }
}
