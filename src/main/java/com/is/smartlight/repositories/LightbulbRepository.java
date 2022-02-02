package com.is.smartlight.repositories;

import com.is.smartlight.models.Lightbulb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LightbulbRepository extends JpaRepository<Lightbulb, Long> {

        @Transactional
        @Modifying
        @Query("UPDATE Lightbulb l set l.deleted = true where l.group.id = :id")
        void deleteByGroupId(Long id);

        @Transactional
        @Modifying
        @Query("UPDATE Lightbulb l set l.deleted = true where l.id = :id")
        void deleteLightbulb(Long id);

        @Transactional
        @Modifying
        @Query("UPDATE Lightbulb l set l.redValue = :rvalue, l.greenValue = :gvalue, l.blueValue = :bvalue, l.intensityPercentage = :percent where l.id = :id")
        void updateLightbulb(Long id, Integer rvalue, Integer gvalue, Integer bvalue, Float percent);

        @Transactional
        @Modifying
        @Query("UPDATE Lightbulb l set l.group.id = :groupId where l.id = :id")
        void changeLightbulbGroupId(Long id, Long groupId);

        @Transactional
        @Modifying
        @Query("UPDATE Lightbulb l set l.turnedOn = :on where l.group.id = :groupId and l.working = true")
        void turnOnOffLightbulbs(Long groupId, Boolean on);
}
