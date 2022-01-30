package com.is.smartlight.controllers;


import com.is.smartlight.dtos.LightGroupDto;
import com.is.smartlight.services.LightGroupService;
import com.is.smartlight.services.LightbulbService;
import com.is.smartlight.utility.KeycloakHelper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("")
    public ResponseEntity<List<LightGroupDto>> getLightGroups(Authentication authentication){
        return ResponseEntity.ok(lightGroupService.getLightGroups((Long.parseLong(KeycloakHelper.getUser(authentication))))
                .stream()
                .map(lightGroup -> modelMapper.map(lightGroup, LightGroupDto.class))
                .collect(Collectors.toList()));
    }

}
