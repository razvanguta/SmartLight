package com.is.smartlight.repositories;

import com.is.smartlight.models.DefaultPreset;
import com.is.smartlight.models.LightGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DefaultPresetRepository extends JpaRepository<DefaultPreset, Long> {

    Optional<DefaultPreset> findById(Long Id);

}
