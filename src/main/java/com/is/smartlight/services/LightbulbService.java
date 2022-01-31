package com.is.smartlight.services;

import com.is.smartlight.dtos.LightbulbDto;
import com.is.smartlight.models.LightGroup;
import com.is.smartlight.models.Lightbulb;
import com.is.smartlight.repositories.LightGroupRepository;
import com.is.smartlight.repositories.LightbulbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LightbulbService {
    private final LightbulbRepository lightbulbRepository;
    private final KeycloakAdminService keycloakAdminService;
    private final LightGroupRepository lightGroupRepository;

    @Autowired
    public LightbulbService(LightbulbRepository lightbulbRepository, KeycloakAdminService keycloakAdminService, LightGroupRepository lightGroupRepository) {
        this.lightbulbRepository = lightbulbRepository;
        this.keycloakAdminService = keycloakAdminService;
        this.lightGroupRepository = lightGroupRepository;
    }

    public void addLightbulbToGroup(LightbulbDto lightbulbDto, Long groupId, Long uid){

        LightGroup lightGroup = lightGroupRepository.findById(groupId).get();
        // check if lightgroup belongs to user
        if(lightGroup.getUser().getId().equals(uid)){
            Lightbulb lightbulb = Lightbulb.builder()
                    .id(lightbulbDto.getId())
                    .redValue(lightbulbDto.getRedValue())
                    .blueValue(lightbulbDto.getBlueValue())
                    .greenValue(lightbulbDto.getGreenValue())
                    .turnedOn(lightbulbDto.getTurnedOn())
                    .isWorking(lightbulbDto.getIsWorking())
                    .maxIntensity(lightbulbDto.getMaxIntensity())
                    .intensityPercentage(lightbulbDto.getIntensityPercentage())
                    .deleted(lightbulbDto.getDeleted())
                    .group(lightGroup)
                    .build();
            lightbulbRepository.save(lightbulb);
        }

    }

    public void deleteLightbulb(Long id, Long uid){
        Lightbulb lightbulb = lightbulbRepository.getById(id);
        LightGroup lightGroup = lightbulb.getGroup();
        if (lightGroup.getUser().getId() == uid) {
            lightbulbRepository.deleteLightbulb(id);
        }
    }
}
