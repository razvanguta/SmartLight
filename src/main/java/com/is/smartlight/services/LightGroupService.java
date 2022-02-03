package com.is.smartlight.services;

import com.is.smartlight.config.MqttGateway;
import com.is.smartlight.dtos.NewLightGroupDto;
import com.is.smartlight.models.*;
import com.is.smartlight.repositories.*;
import com.is.smartlight.models.Lightbulb;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LightGroupService {
    private final LightGroupRepository lightGroupRepository;
    private final KeycloakAdminService keycloakAdminService;
    private final UserRepository userRepository;
    private final LightbulbService lightbulbService;
    private final LightbulbRepository lightbulbRepository;
    private final WeatherService weatherService;
    private final DefaultPresetRepository defaultPresetRepository;
    private final UserPresetRepository userPresetRepository;
    private final UserPresetService userPresetService;
    private final RoutineRepository routineRepository;
    private final DefaultPresetService defaultPresetService;
    private final MqttGateway mqttGateway;

    @Autowired
    public LightGroupService(WeatherService weatherService, LightbulbService lightbulbService, LightGroupRepository lightGroupRepository, KeycloakAdminService keycloakAdminService, UserRepository userRepository, LightbulbRepository lightbulbRepository, DefaultPresetRepository defaultPresetRepository, UserPresetRepository userPresetRepository, UserPresetService userPresetService, DefaultPresetService defaultPresetService, RoutineRepository routineRepository, MqttGateway mqttGateway){
        this.lightGroupRepository = lightGroupRepository;
        this.keycloakAdminService = keycloakAdminService;
        this.userRepository = userRepository;
        this.lightbulbRepository = lightbulbRepository;
        this.lightbulbService = lightbulbService;
        this.weatherService = weatherService;
        this.defaultPresetRepository = defaultPresetRepository;
        this.userPresetRepository = userPresetRepository;
        this.userPresetService = userPresetService;
        this.defaultPresetService = defaultPresetService;
        this.routineRepository = routineRepository;
        this.mqttGateway = mqttGateway;
    }

    public List<LightGroup> getLightGroups(Long id) {
        List<LightGroup> lightGroups =  lightGroupRepository.findAllByUserId(id);

        for(LightGroup lightGroup : lightGroups){
            List<Lightbulb> lightbulbs = new ArrayList<>();

            for(Lightbulb lightbulb : lightGroup.getLightbulbs()){
                if(!lightbulb.getDeleted())
                    lightbulbs.add(lightbulb);
            }
            lightGroup.setLightbulbs(lightbulbs);
        }
        return lightGroups;
    }

    public LightGroup getLightGroup(Long id, Long uid) { return lightGroupRepository.findByUserId(id, uid); }

    public void deleteGroup(Long id, Long uid) {
        LightGroup lightGroup = getLightGroup(id , uid);
        List<Lightbulb> lightbulbs = lightGroup.getLightbulbs();
        for(Lightbulb lightbulb : lightbulbs){
            lightbulbRepository.deleteByGroupId(id);
        }
        lightGroupRepository.deleteByUserId(id, uid);
    }

    public void addGroup(NewLightGroupDto newLightGroupDto, Long uid){

        User user = userRepository.findById(uid).get();

        LightGroup lightGroup = LightGroup.builder()
                .id(newLightGroupDto.getId())
                .name(newLightGroupDto.getName())
                .deleted(false)
                .user(user)
                .build();
        lightGroupRepository.save(lightGroup);

    }

    public void turnOnOffGroupLights(Long groupId, Long uid, Boolean on){
        LightGroup lightGroup = lightGroupRepository.getById(groupId);

        if(lightGroup.getUser().getId() == uid){
            lightbulbRepository.turnOnOffLightbulbs(groupId, on);
            mqttGateway.sendToMqtt("{\"turnedOn\": " + on + "}","/lightgroups/" + groupId);
        }
    }

    public void moveToGroup(Long groupId, Long bulbId, Long uid){

        LightGroup lightGroup = lightGroupRepository.getById(groupId);
        Lightbulb lightbulb = lightbulbRepository.getById(bulbId);

        if (lightbulb.getGroup().getUser().getId() == uid && lightGroup.getUser().getId() == uid){
            lightbulbRepository.changeLightbulbGroupId(bulbId, groupId);
        }
    }

    public LightGroup getLightGroupByName(String name, Long uid){
        return lightGroupRepository.findByNameAndUserId(name, uid);

        //return userPreset;
    }

    @SneakyThrows
    public void setLightGroupIntensity(Long id, int desiredLuminosity, String city){

        Optional<LightGroup> lightgroup = lightGroupRepository.findById(id);
        if(lightgroup.isEmpty())
            throw new NotFoundException("Group not found");
        List<Lightbulb> lightbulbs = lightgroup.get().getLightbulbs();
        if (desiredLuminosity == 0){
            for (Lightbulb lightbulb : lightbulbs)
                lightbulb.setIntensityPercentage(0.0F);
            mqttGateway.sendToMqtt("{\"intensityPercentage\": 0.0}","/lightgroups/" + id);
            return;
        }

        String outsideLuminosityString = weatherService.getOutsideLuminosity(city);
        int intakeLuminosity = Integer.parseInt(outsideLuminosityString.replaceAll("[^0-9]", "")) / 25; //luminozitatea care intra pe fereastra (am aproximat suprafata incaperii la 25m^2,

        if(intakeLuminosity < desiredLuminosity)

        {
            int achievableLuminosity = 0;
            for (Lightbulb lightbulb : lightbulbs){

                achievableLuminosity += lightbulb.getMaxIntensity();
            }
            achievableLuminosity /= 25; //25 m^2
            if (achievableLuminosity < desiredLuminosity){
                throw new Exception("Can't achieve desired luminosity");
            }
            float percentage = (float) desiredLuminosity * 100 / achievableLuminosity ;


            for (Lightbulb lightbulb : lightbulbs) {
                lightbulb.setIntensityPercentage(percentage);
                lightbulbRepository.save(lightbulb);

            }
            mqttGateway.sendToMqtt("{\"intensityPercentage\": " + percentage + "}","/lightgroups/" + id);
        }

    }

    @SneakyThrows
    public void activateRoutine(Long groupId, Long userId) {
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);

        int dayIndex = rightNow.get(Calendar.DAY_OF_WEEK) - 2;
        if (dayIndex == -1) dayIndex = 6;

        int finalDayIndex = dayIndex;
        List<Routine> routineList = routineRepository.findAllByGroupId(groupId).stream().filter(routine -> routine.getDayIndex() == finalDayIndex).collect(Collectors.toList());
        System.out.println(routineList.size());
        for(Routine r: routineList){
            System.out.println(r.getEndHour());
            if(hour <= r.getEndHour() && hour >= r.getStartHour()){
                Long presetId = r.getPresetId();
                Optional<UserPreset> preset = userPresetRepository.findById(presetId);
                if(preset.isPresent()){
                    userPresetService.applyPresetToGroup(presetId,groupId, userId);
                }
                else{
                    defaultPresetService.applyPresetToGroup(presetId, groupId, userId);
                }

            }

        }

    }
}
