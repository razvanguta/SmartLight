package com.is.smartlight.controllers;


import com.is.smartlight.dtos.LightbulbDto;
import com.is.smartlight.models.Lightbulb;
import com.is.smartlight.services.LightbulbService;
import com.is.smartlight.utility.KeycloakHelper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.is.smartlight.utility.HttpStatusUtility.successResponse;

@RestController
@Tag(name = "Lightbulb Controller")
@RequestMapping("/lightbulbs")
public class LightbulbController {
    private final LightbulbService lightbulbService;

    @Autowired
    public LightbulbController(LightbulbService lightbulbService){
        this.lightbulbService = lightbulbService;
    }

    @PostMapping("/add-lightbulb/{groupId}")
    public ResponseEntity<?> addLightbulbToGroup(@RequestBody LightbulbDto lightbulbDto, @PathVariable Long  groupId, Authentication authentication){
        lightbulbService.addLightbulbToGroup(lightbulbDto, groupId, Long.parseLong(KeycloakHelper.getUser(authentication)));
        return successResponse();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLightbulb(@PathVariable Long id, Authentication authentication){
        lightbulbService.deleteLightbulb(id, Long.parseLong(KeycloakHelper.getUser(authentication)));
        return successResponse();
    }

}
