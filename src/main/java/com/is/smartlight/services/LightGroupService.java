package com.is.smartlight.services;

import com.is.smartlight.dtos.LightGroupDto;
import com.is.smartlight.dtos.NewLightGroupDto;
import com.is.smartlight.models.LightGroup;
import com.is.smartlight.models.Lightbulb;
import com.is.smartlight.repositories.EnergyRepository;
import com.is.smartlight.models.Lightbulb;
import com.is.smartlight.models.User;
import com.is.smartlight.repositories.LightGroupRepository;
import com.is.smartlight.repositories.LightbulbRepository;
import com.is.smartlight.repositories.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LightGroupService {
    private final LightGroupRepository lightGroupRepository;
    private final KeycloakAdminService keycloakAdminService;
    private final UserRepository userRepository;
    private final LightbulbService lightbulbService;
    private final LightbulbRepository lightbulbRepository;
    private final WeatherService weatherService;

    @Autowired
    public LightGroupService(WeatherService weatherService, LightbulbService lightbulbService,LightGroupRepository lightGroupRepository, KeycloakAdminService keycloakAdminService, UserRepository userRepository, LightbulbRepository lightbulbRepository){
        this.lightGroupRepository = lightGroupRepository;
        this.keycloakAdminService = keycloakAdminService;
        this.userRepository = userRepository;
        this.lightbulbRepository = lightbulbRepository;
        this.lightbulbService = lightbulbService;
        this.weatherService = weatherService;
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
        lightGroupRepository.deleteByUserId(uid);
    }

    public void addGroup(NewLightGroupDto newLightGroupDto, Long uid){

        User user = userRepository.findById(uid).get();

        LightGroup lightGroup = LightGroup.builder()
                .id(newLightGroupDto.getId())
                .name(newLightGroupDto.getName())
                .deleted(newLightGroupDto.getDeleted())
                .user(user)
                .build();
        lightGroupRepository.save(lightGroup);

    }
    public void moveToGroup(Long groupId, Long bulbId, Long uid){

        LightGroup lightGroup = lightGroupRepository.getById(groupId);
        Lightbulb lightbulb = lightbulbRepository.getById(bulbId);

        if (lightbulb.getGroup().getUser().getId() == uid && lightGroup.getUser().getId() == uid){
            lightbulbRepository.changeLightbulbGroupId(bulbId, groupId);
        }
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
            return;
        }

        String outsideLuminosityString = weatherService.getOutsideLuminosity(city);
        int intakeLuminosity = Integer.parseInt(outsideLuminosityString.replaceAll("[^0-9]", "")) / 25; //luminozitatea care intra pe fereastra (am aproximat suprafata incaperii la 25m^2,
                                                                                // fara legatura cu 25 din formula)
        intakeLuminosity = 0;
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
        }

    }

    public void addRoutine(String dayOfTheWeek, int start, int end){}



}
