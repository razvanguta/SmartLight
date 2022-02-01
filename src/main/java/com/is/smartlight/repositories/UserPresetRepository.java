package com.is.smartlight.repositories;

import com.is.smartlight.models.LightGroup;
import com.is.smartlight.models.UserPreset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserPresetRepository extends JpaRepository<UserPreset, Long> {

    @Query("SELECT p from UserPreset p where p.user.id = :id and p.deleted = false")
    List<UserPreset> findAllByUserId(Long id);

    @Query("SELECT p from UserPreset p where p.user.id = :uid and p.deleted = false and p.id = :id")
    UserPreset findByIdAndUserId(Long id, Long uid);

    @Transactional
    @Modifying
    @Query("UPDATE UserPreset p set p.deleted = true where p.user.id = :uid and p.id = :id")
    void deleteByIdAndUserId(Long id, Long uid);

    @Query("SELECT p from UserPreset p where p.user.id = :id and p.deleted = false and p.name = :name")
    UserPreset findByNameAndUserId(String name, Long id);

    @Transactional
    @Modifying
    @Query("UPDATE UserPreset p set p.deleted = true where p.user.id = :uid and p.name = :name")
    void deleteByNameAndUserId(String name, Long uid);
}
