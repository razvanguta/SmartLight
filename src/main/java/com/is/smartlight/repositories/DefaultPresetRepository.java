package com.is.smartlight.repositories;

import com.is.smartlight.dtos.EnergyDto;
import com.is.smartlight.models.DefaultPreset;
import com.is.smartlight.models.LightGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DefaultPresetRepository extends JpaRepository<DefaultPreset, Long> {

    Optional<DefaultPreset> findById(Long Id);

    List<DefaultPreset> findAll();

    @Query("select p from DefaultPreset p where 1=1")
    List<DefaultPreset> getAll();

    Optional<DefaultPreset> findByName(String name);
}
