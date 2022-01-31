package com.is.smartlight.config;

import com.is.smartlight.dtos.LightGroupDto;
import com.is.smartlight.dtos.LightbulbDto;
import com.is.smartlight.models.LightGroup;
import com.is.smartlight.models.Lightbulb;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

@Configuration
public class ApplicationConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<LightGroupDto, LightGroup> lightGroupConverter = context -> {
            LightGroupDto lgd = context.getSource();
            LightGroup lg = new LightGroup();

            lg.setId(lgd.getId());
            lg.setName(lgd.getName());
            lg.setDeleted(lgd.getDeleted());
            lg.setLightbulbs(lgd.getLightbulbs().stream()
                    .map(lb -> modelMapper.map(lb, Lightbulb.class))
                    .collect(Collectors.toList()));

            return lg;
        };

        Converter<LightbulbDto, Lightbulb> lightbulbConverter = context -> {
            LightbulbDto lbd = context.getSource();
            Lightbulb lb = new Lightbulb();

            lb.setId(lbd.getId());
            lb.setRedValue(lbd.getRedValue());
            lb.setGreenValue(lbd.getGreenValue());
            lb.setBlueValue(lbd.getBlueValue());
            lb.setMaxIntensity(lbd.getMaxIntensity());
            lb.setIntensityPercentage(lbd.getIntensityPercentage());
            lb.setTurnedOn(lbd.getTurnedOn());
            lb.setDeleted(lbd.getDeleted());
            lb.setIsWorking(lbd.getIsWorking());

            return lb;
        };

        modelMapper.addConverter(lightGroupConverter);
        modelMapper.addConverter(lightbulbConverter);

        return modelMapper;
    }
}
