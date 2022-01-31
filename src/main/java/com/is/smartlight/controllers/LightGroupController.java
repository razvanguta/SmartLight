package com.is.smartlight.controllers;


import com.is.smartlight.dtos.LightGroupDto;
import com.is.smartlight.dtos.NewLightGroupDto;
import com.is.smartlight.models.LightGroup;
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

    @Operation(summary = "Move lightbulb in light-group")
    @PutMapping("/move-lightbulb/{groupId}/{bulbId}")
    public ResponseEntity<?> moveLightbulbToGroup(@PathVariable Long groupId, @PathVariable Long bulbId, Authentication authentication){
        lightGroupService.moveToGroup(groupId, bulbId,  Long.parseLong(KeycloakHelper.getUser(authentication)));
        return successResponse();
    }
}
