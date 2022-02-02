package com.is.smartlight.utility;

import com.is.smartlight.models.DefaultPresetsData;
import com.is.smartlight.services.DefaultPresetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
    private final DefaultPresetService defaultPresetService;

    @Autowired
    public DataLoader(DefaultPresetService defaultPresetService) {
        this.defaultPresetService = defaultPresetService;
    }

    public void run(ApplicationArguments args) {
        defaultPresetService.loadDefaultPresetsOnStartup();
    }
}
