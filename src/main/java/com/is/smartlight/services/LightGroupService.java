package com.is.smartlight.services;

import com.is.smartlight.dtos.LightGroupDto;
import com.is.smartlight.models.LightGroup;
import com.is.smartlight.models.Lightbulb;
import com.is.smartlight.repositories.EnergyRepository;
import com.is.smartlight.repositories.LightGroupRepository;
import com.is.smartlight.repositories.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class LightGroupService {
    private final LightGroupRepository lightGroupRepository;
    private final KeycloakAdminService keycloakAdminService;
    private final UserRepository userRepository;
    private final LightbulbService lightbulbService;

    @Autowired
    public LightGroupService(LightGroupRepository lightGroupRepository, KeycloakAdminService keycloakAdminService, UserRepository userRepository, LightbulbService lightbulbService){
        this.lightGroupRepository = lightGroupRepository;
        this.keycloakAdminService = keycloakAdminService;
        this.userRepository = userRepository;
        this.lightbulbService = lightbulbService;
    }

    public List<LightGroup> getLightGroups(Long id) {
        return lightGroupRepository.findAllByUserId(id);
    }

    @SneakyThrows
    public void setLightGroupIntensity(Long id){

        Optional<LightGroup> lightgroup = lightGroupRepository.findById(id);
        if(lightgroup.isEmpty())
            throw new NotFoundException("Group not found");
        List<Lightbulb> lightbulbs = lightgroup.get().getLightbulbs();
        for (Lightbulb lightbulb : lightbulbs) {
            lightbulbService.setLightbulbIntensityByOutsideLuminosity(lightbulb.getId(), "Bucharest", 200);
        }

    }

}
