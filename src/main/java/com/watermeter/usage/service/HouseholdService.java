package com.watermeter.usage.service;

import com.watermeter.usage.entity.Household;

import java.util.List;

public interface HouseholdService {

    Household createHousehold(Household household);
    public List<Household> getAllHouseholds();


    public void deleteHousehold(Long id);
}
