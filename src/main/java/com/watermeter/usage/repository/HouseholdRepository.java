package com.watermeter.usage.repository;

import com.watermeter.usage.entity.Household;
import com.watermeter.usage.entity.WaterUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseholdRepository extends JpaRepository<Household, Long> {




}
