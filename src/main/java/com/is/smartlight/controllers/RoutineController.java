package com.is.smartlight.controllers;

import com.is.smartlight.dtos.LightbulbDto;
import com.is.smartlight.dtos.RoutineDto;
import com.is.smartlight.services.LightbulbService;
import com.is.smartlight.services.RoutineService;
import com.is.smartlight.utility.KeycloakHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.is.smartlight.utility.HttpStatusUtility.successResponse;

@RestController
@Tag(name = "Routine Controller")
@RequestMapping("/routines")
public class RoutineController {
    private final RoutineService routineService;

    @Autowired
    public RoutineController(RoutineService routineService){
        this.routineService = routineService;
    }

    @PostMapping("/addRoutine/{groupId}")
    public ResponseEntity<?> addRoutineToGroup(@RequestBody RoutineDto routineDto, @PathVariable Long  groupId, Authentication authentication){
        routineService.addRoutine( groupId, routineDto, Long.parseLong(KeycloakHelper.getUser(authentication)));
        return successResponse();
    }

    @Operation(summary = "Deletes the given routine")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoutine(@PathVariable Long id, Authentication authentication){
        routineService.deleteRoutine(id, Long.parseLong(KeycloakHelper.getUser(authentication)));
        return successResponse();
    }


}
