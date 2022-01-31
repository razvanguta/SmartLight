package com.is.smartlight.services;

import com.is.smartlight.dtos.LightGroupDto;
import com.is.smartlight.dtos.NewLightGroupDto;
import com.is.smartlight.models.LightGroup;
import com.is.smartlight.models.Lightbulb;
import com.is.smartlight.models.User;
import com.is.smartlight.repositories.LightGroupRepository;
import com.is.smartlight.repositories.LightbulbRepository;
import com.is.smartlight.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LightGroupService {
    private final LightGroupRepository lightGroupRepository;
    private final KeycloakAdminService keycloakAdminService;
    private final UserRepository userRepository;
    private final LightbulbRepository lightbulbRepository;

    @Autowired
    public LightGroupService(LightGroupRepository lightGroupRepository, KeycloakAdminService keycloakAdminService, UserRepository userRepository, LightbulbRepository lightbulbRepository){
        this.lightGroupRepository = lightGroupRepository;
        this.keycloakAdminService = keycloakAdminService;
        this.userRepository = userRepository;
        this.lightbulbRepository = lightbulbRepository;
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
}
