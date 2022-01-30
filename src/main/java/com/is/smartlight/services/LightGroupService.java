package com.is.smartlight.services;

import com.is.smartlight.dtos.LightGroupDto;
import com.is.smartlight.models.LightGroup;
import com.is.smartlight.repositories.EnergyRepository;
import com.is.smartlight.repositories.LightGroupRepository;
import com.is.smartlight.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LightGroupService {
    private final LightGroupRepository lightGroupRepository;
    private final KeycloakAdminService keycloakAdminService;
    private final UserRepository userRepository;

    @Autowired
    public LightGroupService(LightGroupRepository lightGroupRepository, KeycloakAdminService keycloakAdminService, UserRepository userRepository){
        this.lightGroupRepository = lightGroupRepository;
        this.keycloakAdminService = keycloakAdminService;
        this.userRepository = userRepository;
    }

    public List<LightGroup> getLightGroups(Long id) {
        return lightGroupRepository.findAllByUserId(id);
    }

}
