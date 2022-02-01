package com.is.smartlight.repositories;

import com.is.smartlight.dtos.LightGroupDto;
import com.is.smartlight.models.LightGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface LightGroupRepository extends JpaRepository<LightGroup, Long> {

    Optional<LightGroup> findById(Long id);

    @Query("SELECT l from LightGroup l where l.user.id = :id and l.deleted = false")
    List<LightGroup> findAllByUserId(Long id);

    @Query("SELECT l from LightGroup  l where l.user.id = :uid and l.id = :id")
    LightGroup findByUserId(Long id, Long uid);

    @Transactional
    @Modifying
    @Query("UPDATE LightGroup l set l.deleted = true where l.user.id = :uid and l.id = :id")
    void deleteByUserId(Long id, Long uid);
}
