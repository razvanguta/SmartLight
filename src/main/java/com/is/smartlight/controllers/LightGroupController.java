package com.is.smartlight.controllers;


import com.is.smartlight.dtos.LightGroupDto;
import com.is.smartlight.dtos.NewLightGroupDto;
import com.is.smartlight.dtos.PresetDto;
import com.is.smartlight.models.LightGroup;
import com.is.smartlight.models.UserPreset;
import com.is.smartlight.services.LightGroupService;
import com.is.smartlight.services.LightbulbService;
import com.is.smartlight.utility.KeycloakHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.is.smartlight.utility.HttpStatusUtility.successResponse;

@RestController
@Tag(name = "LightGroup Controller")
@RequestMapping("/lightgroups")
public class LightGroupController {
    private final LightGroupService lightGroupService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public LightGroupController(LightGroupService lightGroupService, ModelMapper modelMapper){
        this.lightGroupService = lightGroupService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Get all lightgroups that are not deleted.")
    @GetMapping("")
    public ResponseEntity<List<LightGroupDto>> getLightGroups(Authentication authentication){
        return ResponseEntity.ok(lightGroupService.getLightGroups((Long.parseLong(KeycloakHelper.getUser(authentication))))
                .stream()
                .map(lightGroup -> modelMapper.map(lightGroup, LightGroupDto.class))
                .collect(Collectors.toList()));
    }
    @PatchMapping("/weather/{id}/{luminosity}")
    public ResponseEntity<?> setIntensityByOutsideWeather(@PathVariable Long id, @PathVariable Integer luminosity, @RequestParam String city){
        lightGroupService.setLightGroupIntensity(id, luminosity, "Bucharest");
        return successResponse();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLightGroup(@PathVariable Long id, Authentication authentication){
        lightGroupService.deleteGroup(id, Long.parseLong(KeycloakHelper.getUser(authentication)));
        return successResponse();
    }

    @Operation(summary = "Create light-group")
    @PostMapping("/add-group")
    public ResponseEntity<?> addLightGroup(@RequestBody NewLightGroupDto newLightGroupDto, Authentication authentication){
        lightGroupService.addGroup(newLightGroupDto, Long.parseLong(KeycloakHelper.getUser(authentication)));
        return successResponse();
    }

    @Operation(summary = "Get light group by name")
    @GetMapping("/getGroup/{groupName}")
    public LightGroupDto getUserPresetId(@PathVariable String groupName, Authentication authentication){
        LightGroup lightGroup = lightGroupService.getLightGroupByName(groupName, Long.parseLong(KeycloakHelper.getUser(authentication)));
        LightGroupDto lightGroupDto = modelMapper.map(lightGroup, LightGroupDto.class);
        return lightGroupDto;
    }

    @Operation(summary = "Move lightbulb in light-group")
    @PutMapping("/move-lightbulb/{groupId}/{bulbId}")
    public ResponseEntity<?> moveLightbulbToGroup(@PathVariable Long groupId, @PathVariable Long bulbId, Authentication authentication){
        lightGroupService.moveToGroup(groupId, bulbId,  Long.parseLong(KeycloakHelper.getUser(authentication)));
        return successResponse();
    }

    @Operation(summary = "Turn on group lights")
    @PutMapping("/turn-on/{groupId}")
    public ResponseEntity<?> turnOnGroupLights(@PathVariable Long groupId, Authentication authentication){
        lightGroupService.turnOnOffGroupLights(groupId, Long.parseLong(KeycloakHelper.getUser(authentication)), true);
        return successResponse();
    }

    @Operation(summary = "Turn off group lights")
    @PutMapping("/turn-off/{groupId}")
    public ResponseEntity<?> turnOffGroupLights(@PathVariable Long groupId, Authentication authentication){
        lightGroupService.turnOnOffGroupLights(groupId, Long.parseLong(KeycloakHelper.getUser(authentication)), false);
        return successResponse();
    }
}
