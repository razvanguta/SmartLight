package com.is.smartlight.repositories;

import com.is.smartlight.dtos.LightGroupDto;
import com.is.smartlight.models.LightGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LightGroupRepository extends JpaRepository<LightGroup, Long> {

    Optional<LightGroup> findById(Long id);

    @Query("SELECT l from LightGroup l where l.user.id = :id ")
    List<LightGroup> findAllByUserId(Long id);
}
