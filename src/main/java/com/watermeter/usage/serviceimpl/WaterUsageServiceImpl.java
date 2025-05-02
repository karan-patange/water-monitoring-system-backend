package com.watermeter.usage.serviceimpl;

import com.watermeter.usage.entity.Household;
import com.watermeter.usage.entity.WaterUsage;
import com.watermeter.usage.repository.HouseholdRepository;
import com.watermeter.usage.repository.WaterUsesRepository;
import com.watermeter.usage.service.WaterUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WaterUsageServiceImpl implements WaterUsageService {

    @Autowired
    private WaterUsesRepository usageRepo;

    @Autowired
    private HouseholdRepository householdRepo;

    @Override
    public WaterUsage recordUsage(Long householdId, WaterUsage usage) {
        Household household = householdRepo.findById(householdId)
                .orElseThrow(() -> new RuntimeException("Household not found"));

        usage.setDate(LocalDateTime.now());
        usage.setHousehold(household);

        return usageRepo.save(usage);
    }

    @Override
    public List<WaterUsage> getAllUsage() {
        return usageRepo.findAll();
    }

    @Override
    public List<WaterUsage> getUsageByHouseholdId(Long householdId) {
        return usageRepo.findByHouseholdId(householdId);
    }
}
