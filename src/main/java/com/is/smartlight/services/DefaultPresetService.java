package com.is.smartlight.services;

import com.is.smartlight.models.*;
import com.is.smartlight.repositories.DefaultPresetRepository;
import com.is.smartlight.repositories.LightGroupRepository;
import com.is.smartlight.repositories.LightbulbRepository;
import com.is.smartlight.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class DefaultPresetService {
    private final DefaultPresetRepository defaultPresetRepository;
    private final UserRepository userRepository;
    private final LightGroupRepository lightGroupRepository;
    private final LightbulbRepository lightbulbRepository;

    @Autowired
    public DefaultPresetService(DefaultPresetRepository defaultPresetRepository, UserRepository userRepository, LightGroupRepository lightGroupRepository, LightbulbRepository lightbulbRepository){
        this.defaultPresetRepository = defaultPresetRepository;
        this.userRepository = userRepository;
        this.lightGroupRepository = lightGroupRepository;
        this.lightbulbRepository = lightbulbRepository;
    }

    public List<DefaultPreset> getDefaultPresets() {
        List<DefaultPreset> defaultPresets = defaultPresetRepository.getAll();

        //TODO: change this (inheritance strategy or make defaultpreset subclass)
        List<DefaultPreset> onlyDefaults = new ArrayList<DefaultPreset>();
        for(DefaultPreset p : defaultPresets) {
            if(!(p instanceof UserPreset)){
                onlyDefaults.add(p);
            }
        }

        return onlyDefaults;
    }

    public DefaultPreset getDefaultPresetByName(String name) {
        return defaultPresetRepository.findDefaultByName(name);
    }

    public void loadDefaultPresetsOnStartup() {
        //Check if default presets were already loaded
        Optional<DefaultPreset> defaultPreset = defaultPresetRepository.findByName("default");

        //Execute if the default preset does not exist
        //Since a default preset can not be deleted or modified, one missing preset means that all the others are missing too
        if(defaultPreset.isEmpty()){
            for(DefaultPresetsData p : DefaultPresetsData.values()){
                DefaultPreset defaultPresetInsert = DefaultPreset.builder()
                        .id(0L)
                        .name(p.toString())
                        .deleted(false)
                        .redValue(p.getR())
                        .greenValue(p.getG())
                        .blueValue(p.getB())
                        .intensityPercentage(p.getIntensity())
                        .build();
                defaultPresetRepository.save(defaultPresetInsert);
            }
        }
    }

    public void applyPresetToGroup(Long presetId, Long groupId, Long uid){
        LightGroup lightGroup = lightGroupRepository.findByUserId(groupId, uid);
        Optional<DefaultPreset> defaultPreset = defaultPresetRepository.findById(presetId);

        if(defaultPreset.isPresent()){
            //if the random preset has been selected, replace the values with random values
            if(defaultPreset.get().getName().equals("Random")){
                Random generator = new Random();
                defaultPreset.get().setRedValue(generator.nextInt(256));
                defaultPreset.get().setBlueValue(generator.nextInt(256));
                defaultPreset.get().setGreenValue(generator.nextInt(256));

                //generates random intensity value and truncates to two decimals
                DecimalFormat df = new DecimalFormat("#.#");
                float randomIntensity = Float.parseFloat(df.format(generator.nextFloat()));
                defaultPreset.get().setIntensityPercentage(randomIntensity);
            }

            for(Lightbulb l : lightGroup.getLightbulbs()){
                lightbulbRepository.updateLightbulb(l.getId(),
                        defaultPreset.get().getRedValue(),
                        defaultPreset.get().getGreenValue(),
                        defaultPreset.get().getBlueValue(),
                        defaultPreset.get().getIntensityPercentage());
            }
        }
        //TODO: default preset not present

    }
}
