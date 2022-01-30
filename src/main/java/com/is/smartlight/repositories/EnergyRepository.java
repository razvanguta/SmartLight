package com.is.smartlight.repositories;

import com.is.smartlight.dtos.EnergyDto;
import com.is.smartlight.models.Energy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnergyRepository extends JpaRepository<Energy, Long> {

    @Query("select new com.is.smartlight.dtos.EnergyDto(e.price, e.date) from Energy e WHERE e.user.id = :id")
    List<EnergyDto> findPriceAndDateByUserId(Long id);

}
