package com.is.smartlight.services;

import com.is.smartlight.dtos.EnergyDto;
import com.is.smartlight.models.Energy;
import com.is.smartlight.models.User;
import com.is.smartlight.repositories.EnergyRepository;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class EnergyService {
    private final EnergyRepository energyRepository;
    private final KeycloakAdminService keycloakAdminService;

    @Autowired
    public EnergyService(EnergyRepository energyRepository, KeycloakAdminService keycloakAdminService){
        this.energyRepository = energyRepository;
        this.keycloakAdminService = keycloakAdminService;
    }

    @SneakyThrows
    public void addEnergyPrice(EnergyDto energyDto, User user){
        //generate a random number of used kWh
        Random rand = new Random();
        Float price = energyDto.getPricePerkWh() * rand.nextInt(1000);
        Energy energy = Energy.builder()
                        .id(rand.nextLong())
                        .price(price)
                        .date(energyDto.getDate())
                        .user(user)
                        .build();
        energyRepository.save(energy);
    }

    @SneakyThrows
    public List<Object[]>  getEnergyPrice(Long id){
        return energyRepository.findPriceAndDateByUserId(id);
    }
}
