package com.is.smartlight.controllers;
import com.is.smartlight.models.Energy;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.core.Authentication;
import com.is.smartlight.dtos.EnergyDto;
import com.is.smartlight.dtos.RegisterDto;
import com.is.smartlight.models.User;
import com.is.smartlight.services.EnergyService;
import com.is.smartlight.utility.KeycloakHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.is.smartlight.utility.HttpStatusUtility.successResponse;

@RestController
@Tag(name = "Energy Controller", description = "Set of endpoints for add and see Energy cost.")
@RequestMapping("/energy")
public class EnergyController {
    private final EnergyService energyService;

    @Autowired
    public EnergyController(EnergyService energyService){
        this.energyService = energyService;
    }

    @Operation(summary = "Add energy cost in history.")
    @PostMapping("/add-energy")
    public ResponseEntity<?> addEnergy(@RequestBody EnergyDto energyDto, Authentication authentication) {

        energyService.addEnergyPrice(energyDto, Long.parseLong(KeycloakHelper.getUser(authentication)));
        return successResponse();
    }

    @Operation(summary = "See the user energy cost history.")
    @GetMapping("/get-energy")
    public List<EnergyDto>  addEnergy(Authentication authentication) {

        return energyService.getEnergyPrice(Long.parseLong(KeycloakHelper.getUser(authentication)));
    }
}
