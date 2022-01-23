package com.is.smartlight.repositories;

import com.is.smartlight.models.Energy;
import com.is.smartlight.models.User;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnergyRepository extends JpaRepository<Energy, Long> {
    @Query("select e.price, e.date from Energy e WHERE e.user.id = ?1")
    List<Object[]> findPriceAndDateByUserId(Long id);
}
