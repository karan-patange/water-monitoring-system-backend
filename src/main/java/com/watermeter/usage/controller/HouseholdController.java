package com.watermeter.usage.controller;

import com.watermeter.usage.entity.Household;
import com.watermeter.usage.entity.Login;
import com.watermeter.usage.entity.WaterUsage;
import com.watermeter.usage.repository.HouseholdRepository;
import com.watermeter.usage.repository.LoginRespository;
import com.watermeter.usage.repository.WaterUsesRepository;
import com.watermeter.usage.service.EmailService;
import com.watermeter.usage.service.HouseholdService;
import com.watermeter.usage.service.WaterUsageService;
import com.watermeter.usage.serviceimpl.WaterUsageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/household")
public class HouseholdController {


@Autowired
    HouseholdService householdService;


Household household = new Household();


@Autowired
    LoginRespository loginRepository;


    @PostMapping("/loginwithrole")
    public ResponseEntity<String> login(
            @RequestParam String userName,
            @RequestParam String passWord,
            @RequestParam String role
    ) {
        Optional<Login> user = loginRepository.findByUserNameAndPassWordAndRole(userName,passWord,role);

        if (user.isPresent()) {
            return ResponseEntity.ok("Login successful as " + role);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials or role");
        }
    }


@Autowired
HouseholdRepository householdRepository;




    @DeleteMapping("/deletehousehold/{id}")
    public ResponseEntity<?> deleteHousehold(@PathVariable Long id) {
        try {
            householdService.deleteHousehold(id); // Assuming you call service method
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting household: " + e.getMessage());
        }
    }


    @PostMapping("/newHousehold")
    public ResponseEntity<Household> addHousehold(@RequestBody Household household){

Household addHousehold = householdService.createHousehold(household);
    return ResponseEntity.status(HttpStatus.CREATED).body(addHousehold);
}

@Autowired
    WaterUsageService usageService;

@Autowired
    EmailService emailservice;

@Autowired
    HouseholdRepository householdRepo;

@Autowired
    WaterUsesRepository usageRepo;

    @PostMapping("/{householdId}")
    public WaterUsage recordUsage(@PathVariable Long householdId, @RequestBody WaterUsage usage) {
        return usageService.recordUsage(householdId, usage);
    }

    @GetMapping("/getAllHouseholds")
    public List<Household> getAllHouseholds() {
        return householdService.getAllHouseholds();
    }

    @Autowired
    WaterUsageServiceImpl waterUsageService;






}
