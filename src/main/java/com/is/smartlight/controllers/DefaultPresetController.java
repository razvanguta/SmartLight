package com.is.smartlight.controllers;

import com.is.smartlight.dtos.PresetDto;
import com.is.smartlight.services.DefaultPresetService;
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
@Tag(name = "DefaultPreset Controller")
@RequestMapping("/defaultpresets")
public class DefaultPresetController {
    private final DefaultPresetService defaultPresetService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public DefaultPresetController(DefaultPresetService defaultPresetService, ModelMapper modelMapper) {
        this.defaultPresetService = defaultPresetService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Get all the default presets")
    @GetMapping("")
    public ResponseEntity<List<PresetDto>> getDefaultPresets() {
        return ResponseEntity.ok(defaultPresetService.getDefaultPresets()
                .stream()
                .map(defaultPreset -> modelMapper.map(defaultPreset, PresetDto.class))
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Apply default preset to group")
    @PutMapping("/apply-preset/{presetId}/{groupId}")
    public ResponseEntity<?> applyDefaultPreset(@PathVariable Long presetId, @PathVariable Long groupId, Authentication authentication) {
        defaultPresetService.applyPresetToGroup(presetId, groupId, Long.parseLong(KeycloakHelper.getUser(authentication)));
        return successResponse();
    }
}
