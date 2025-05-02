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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class HouseholdServiceImpl implements HouseholdService {

    private static final Logger log = LoggerFactory.getLogger(HouseholdServiceImpl.class);

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private LoginRespository loginRespository;

    @Autowired
    private WaterUsesRepository waterUsesRepository;

    public static String generateEightDigitNumber() {
        Random random = new Random();
        int number = 10000000 + random.nextInt(90000000); // ensures 8 digits
        return String.valueOf(number);
    }

    @Override
    public Household createHousehold(Household household) {
        String username = generateEightDigitNumber();
        Login login = new Login();
        login.setPassWord("password");
        login.setUserName(username);
        login.setRole("household");
        login.setHousehold(household);
        household.setLogin(login);
        Household saveHousehold = householdRepository.save(household);
        emailService.WelcomeMail(household.getEmail(), login.getUserName(), login.getPassWord());
        return saveHousehold;
    }

    @Override
    public List<Household> getAllHouseholds() {
        return householdRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteHousehold(Long id) {
        householdRepository.deleteById(id);
    }

    public ResponseEntity<?> getHouseholdByUsername(String username) {
        Optional<Login> loginOptional = loginRespository.findByUserName(username);

        if (loginOptional.isPresent() && loginOptional.get().getHousehold() != null) {
            return ResponseEntity.ok(loginOptional.get().getHousehold());
        } else {
            return ResponseEntity.status(404).body("Customer not found for account number: " + username);
        }
    }
}
