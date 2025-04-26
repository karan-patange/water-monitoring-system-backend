package com.watermeter.usage.serviceimpl;

import com.watermeter.usage.entity.Household;
import com.watermeter.usage.entity.Login;
import com.watermeter.usage.repository.HouseholdRepository;
import com.watermeter.usage.repository.LoginRespository;
import com.watermeter.usage.repository.WaterUsesRepository;
import com.watermeter.usage.service.EmailService;
import com.watermeter.usage.service.HouseholdService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseholdServiceImpl implements HouseholdService {

    private static final Logger log = LoggerFactory.getLogger(HouseholdServiceImpl.class);
    @Autowired
HouseholdRepository householdRepository;

@Autowired
EmailService emailService;

@Autowired
    LoginRespository loginRespository;

Login login = new Login();

    @Override
    public Household createHousehold(Household household) {
        Household saveHousehold = householdRepository.save(household);
        login.setPassWord("password");
        login.setUserName(household.getEmail());
        login.setRole("CUSTOMER");
        loginRespository.save(login);
                emailService.WelcomeMail(household.getEmail(),login.getUserName(),login.getPassWord());
        return saveHousehold;

    }

    @Override
    public List<Household> getAllHouseholds() {
        return householdRepository.findAll();
    }


    @Autowired
    WaterUsesRepository waterUsesRepository;

@Override
@Transactional
public void deleteHousehold(Long id) {


    // Then delete the household
    householdRepository.deleteById(id);
}
}
