package com.is.smartlight.repositories;

import com.is.smartlight.models.Lightbulb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LightbulbRepository extends JpaRepository<Lightbulb, Long> {

}
