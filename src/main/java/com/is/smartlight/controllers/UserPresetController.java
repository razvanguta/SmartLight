package com.is.smartlight.controllers;

import com.is.smartlight.dtos.LightGroupDto;
import com.is.smartlight.dtos.NewPresetDto;
import com.is.smartlight.dtos.PresetDto;
import com.is.smartlight.models.UserPreset;
import com.is.smartlight.services.UserPresetService;
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
@Tag(name = "UserPreset Controller")
@RequestMapping("/userpresets")
public class UserPresetController {
    private final UserPresetService userPresetService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public UserPresetController(UserPresetService userPresetService, ModelMapper modelMapper){
        this.userPresetService = userPresetService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Get all custom user presets that are not deleted")
    @GetMapping("")
    public ResponseEntity<List<PresetDto>> getUserPresets(Authentication authentication){
        return ResponseEntity.ok(userPresetService.getUserPresets((Long.parseLong(KeycloakHelper.getUser(authentication))))
                .stream()
                .map(userPreset -> modelMapper.map(userPreset, PresetDto.class))
                .collect(Collectors.toList()));
    }


    @Operation(summary = "Deletes the given preset")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserPreset(@PathVariable Long id, Authentication authentication){
        userPresetService.deletePresetById(id, Long.parseLong(KeycloakHelper.getUser(authentication)));
        return successResponse();
    }


    @Operation(summary = "Create a custom user preset")
    @PostMapping("/add-preset")
    public ResponseEntity<?> addUserPreset(@RequestBody NewPresetDto newPresetDto, Authentication authentication){
        userPresetService.createUserPreset(newPresetDto, Long.parseLong(KeycloakHelper.getUser(authentication)));
        return successResponse();
    }

    @Operation(summary = "Apply custom preset to light group")
    @PostMapping("/apply-preset/{presetId}/{groupId}")
    public ResponseEntity<?> applyUserPreset(@PathVariable Long presetId, @PathVariable Long groupId, Authentication authentication){
        userPresetService.applyPresetToGroup(presetId, groupId, Long.parseLong(KeycloakHelper.getUser(authentication)));
        return successResponse();
    }
}
