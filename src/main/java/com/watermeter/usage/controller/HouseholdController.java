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
import com.watermeter.usage.serviceimpl.HouseholdServiceImpl;
import com.watermeter.usage.serviceimpl.WaterUsageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Optional<Login> user = loginRepository.findByUserNameAndPassWordAndRole(userName, passWord, role);

        if (user.isPresent()) {
            return ResponseEntity.ok("Login successful as " + role);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials or role");
        }
    }


//    @PostMapping("/loginwithrole")
//    public ResponseEntity<Map<String, Object>> login(
//            @RequestParam String userName,
//            @RequestParam String passWord,
//            @RequestParam String role
//    ) {
//        Optional<Login> user = loginRepository.findByUserNameAndPassWordAndRole(userName, passWord, role);
//
//        // If user is found
//        if (user.isPresent()) {
//            Login login = user.get();
//
//            // Prepare response data
//            Map<String, Object> response = new HashMap<>();
//            response.put("id", login.getHousehold().getId());
//            response.put("message", "Login successful");
//            response.put("username", login.getUserName());
//            response.put("role", login.getRole());
//
//
////            // Check if the role is "household" and get the householdId
////            if ("household".equals(login.getRole())) {
////                Household household = login.getHousehold(); // Assuming Login entity has a reference to Household
////                if (household != null) {
////                    response.put("householdId", household.getId());  // Get the household ID
////                }
////            }
//
//            return ResponseEntity.ok(response);
//        } else {
//            // If credentials are invalid
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials or role"));
//        }
//    }

@Autowired
    HouseholdServiceImpl householdServiceimpl;

    @GetMapping("/getHouseholdByUsername/{username}")
    public ResponseEntity<?> getCustomerByAccountNumber(@PathVariable String username) {
        return householdServiceimpl.getHouseholdByUsername(username);
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



    @GetMapping("/waterUsage/{householdId}")
    public ResponseEntity<List<WaterUsage>> getUsageByHousehold(@PathVariable Long householdId) {
        List<WaterUsage> usageList = waterUsageService.getUsageByHouseholdId(householdId);
        return ResponseEntity.ok(usageList);
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



    @PostMapping("/addWaterUsage/{householdId}")
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
