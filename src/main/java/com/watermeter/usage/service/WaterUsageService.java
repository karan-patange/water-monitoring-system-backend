package com.watermeter.usage.service;

import com.watermeter.usage.entity.WaterUsage;
import java.util.List;

public interface WaterUsageService {

    WaterUsage recordUsage(Long householdId, WaterUsage usage);

    List<WaterUsage> getAllUsage();

    List<WaterUsage> getUsageByHouseholdId(Long householdId);
}
