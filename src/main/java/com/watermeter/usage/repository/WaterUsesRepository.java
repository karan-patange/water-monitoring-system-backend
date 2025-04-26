package com.watermeter.usage.repository;

import com.watermeter.usage.entity.Household;
import com.watermeter.usage.entity.WaterUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WaterUsesRepository extends JpaRepository<WaterUsage, Long> {

    List<WaterUsage> findByHousehold(Household household);




}
