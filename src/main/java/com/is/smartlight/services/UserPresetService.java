package com.is.smartlight.services;

import com.is.smartlight.config.MqttGateway;
import com.is.smartlight.dtos.NewPresetDto;
import com.is.smartlight.dtos.PresetDto;
import com.is.smartlight.models.LightGroup;
import com.is.smartlight.models.Lightbulb;
import com.is.smartlight.models.User;
import com.is.smartlight.models.UserPreset;
import com.is.smartlight.repositories.LightGroupRepository;
import com.is.smartlight.repositories.LightbulbRepository;
import com.is.smartlight.repositories.UserPresetRepository;
import com.is.smartlight.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPresetService {
    private final UserPresetRepository userPresetRepository;
    private final UserRepository userRepository;
    private final LightGroupRepository lightGroupRepository;
    private final LightbulbRepository lightbulbRepository;
    private final MqttGateway mqttGateway;

    @Autowired
    public UserPresetService(UserPresetRepository userPresetRepository, UserRepository userRepository, LightGroupRepository lightGroupRepository, LightbulbRepository lightbulbRepository, MqttGateway mqttGateway){
        this.userPresetRepository = userPresetRepository;
        this.userRepository = userRepository;
        this.lightGroupRepository = lightGroupRepository;
        this.lightbulbRepository = lightbulbRepository;
        this.mqttGateway = mqttGateway;
    }


    public List<UserPreset> getUserPresets(Long id) {
        List<UserPreset> userPresets = userPresetRepository.findAllByUserId(id);

        return userPresets;
    }


    public void createUserPreset(NewPresetDto newPresetDto, Long uid){
        User user = userRepository.findById(uid).get();

        UserPreset userPreset = UserPreset.userPresetBuilder()
                .id(newPresetDto.getId())
                .name(newPresetDto.getName())
                .deleted(false)
                .redValue(newPresetDto.getRedValue())
                .greenValue(newPresetDto.getGreenValue())
                .blueValue(newPresetDto.getBlueValue())
                .intensityPercentage(newPresetDto.getIntensityPercentage())
                .user(user)
                .build();

        userPresetRepository.save(userPreset);
    }


    public UserPreset getUserPresetById(Long id, Long uid){
        UserPreset userPreset = userPresetRepository.findByIdAndUserId(id, uid);

        return userPreset;
    }


    public UserPreset getUserPresetByName(String name, Long uid){
        return userPresetRepository.findByNameAndUserId(name, uid);

        //return userPreset;
    }


    public void deletePresetByName(String name, Long uid){
        userPresetRepository.deleteByNameAndUserId(name, uid);
    }


    public void deletePresetById(Long id, Long uid){
        userPresetRepository.deleteByIdAndUserId(id, uid);
    }


    public void applyPresetToGroup(Long presetId, Long groupId, Long uid){
        LightGroup lightGroup = lightGroupRepository.findByUserId(groupId, uid);
        UserPreset userPreset = userPresetRepository.findByIdAndUserId(presetId, uid);

        for(Lightbulb l : lightGroup.getLightbulbs()){
            lightbulbRepository.updateLightbulb(l.getId(),
                    userPreset.getRedValue(),
                    userPreset.getGreenValue(),
                    userPreset.getBlueValue(),
                    userPreset.getIntensityPercentage());
        }
        mqttGateway.sendToMqtt("{" +
                "\"redValue\": " + userPreset.getRedValue() + "," +
                "\"greenValue\": " + userPreset.getGreenValue() + "," +
                "\"blueValue\": " + userPreset.getBlueValue() + "," +
                "\"intensityPercentage\": " + userPreset.getIntensityPercentage() +
                "}","/lightgroups/" + groupId);
    }
}
