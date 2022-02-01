package com.is.smartlight.services;

import com.is.smartlight.dtos.RoutineDto;
import com.is.smartlight.models.DefaultPreset;
import com.is.smartlight.models.LightGroup;
import com.is.smartlight.models.Lightbulb;
import com.is.smartlight.models.Routine;
import com.is.smartlight.repositories.DefaultPresetRepository;
import com.is.smartlight.repositories.LightGroupRepository;
import com.is.smartlight.repositories.RoutineRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoutineService {
    private final RoutineRepository routineRepository;
    private final KeycloakAdminService keycloakAdminService;
    private final LightGroupService lightGroupService;
    private final LightGroupRepository lightGroupRepository;
    private final DefaultPresetRepository defaultPresetRepository;

    @Autowired
    public RoutineService(RoutineRepository routineRepository, KeycloakAdminService keycloakAdminService, LightGroupService lightGroupService, LightGroupRepository lightGroupRepository, DefaultPresetRepository defaultPresetRepository) {
        this.routineRepository = routineRepository;
        this.keycloakAdminService = keycloakAdminService;
        this.lightGroupService = lightGroupService;
        this.lightGroupRepository = lightGroupRepository;
        this.defaultPresetRepository = defaultPresetRepository;
    }

    @SneakyThrows
    public void addRoutine(Long groupId, RoutineDto routineDto, Long userId) {
        int dayIndex;
        String dayOfTheWeek = routineDto.getDayOfTheWeek();
        System.out.println(dayOfTheWeek);
        switch (dayOfTheWeek) {
            case "Monday":
                dayIndex = 0;
                break;
            case "Tuesday":
                dayIndex = 1;
                break;
            case "Wednesday":
                dayIndex = 2;
                break;
            case "Thursday":
                dayIndex = 3;
                break;
            case "Friday":
                dayIndex = 4;
                break;
            case "Saturday":
                dayIndex = 5;
                break;
            case "Sunday":
                dayIndex = 6;
                break;
            default:
                throw new Exception("Not a day. Please write capitalized");
        }
        LightGroup lightGroup = lightGroupRepository.findById(groupId).get();

        int startHour = routineDto.getStartHour();
        int endHour = routineDto.getEndHour();
        Long presetId = routineDto.getPresetId();


        List<Routine> routineList = lightGroup.getRoutines().stream().filter(routine -> routine.getDayIndex() == dayIndex).collect(Collectors.toList());
        for(Routine r: routineList){
            if(startHour > r.getEndHour() || endHour < r.getStartHour()){
            }
            else{
                throw new Exception("Not an available interval");
            }

        }


        Routine routine = Routine.builder()
                .group(lightGroup)
                .startHour(startHour)
                .endHour(endHour)
                .presetId(presetId)
                .dayIndex(dayIndex)
                .build();


        routineRepository.save(routine);
        Thread.sleep(2000);
        lightGroupService.activateRoutine(groupId, userId);

    }

    public void deleteRoutine(Long id, Long userId){
        routineRepository.deleteByIdAndUserId(id, userId);
    }
}

