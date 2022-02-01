package com.is.smartlight.repositories;

import com.is.smartlight.models.Routine;
import com.is.smartlight.models.UserPreset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RoutineRepository extends JpaRepository<Routine, Long> {

    @Query("SELECT r from Routine r where r.group.id = :groupId")
    List<Routine> findAllByGroupId(Long groupId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Routine r WHERE r.group.user.id = :userId and r.id = :id")
    void deleteByIdAndUserId(Long id, Long userId);
}
