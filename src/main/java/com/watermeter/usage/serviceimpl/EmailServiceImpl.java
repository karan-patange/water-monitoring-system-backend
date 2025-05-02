package com.watermeter.usage.serviceimpl;

import com.watermeter.usage.entity.Household;
import com.watermeter.usage.entity.WaterUsage;
import com.watermeter.usage.repository.HouseholdRepository;
import com.watermeter.usage.repository.WaterUsesRepository;
import com.watermeter.usage.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    HouseholdRepository householdRepo;

    @Autowired
    WaterUsesRepository usageRepo;

    @Override
    public void WelcomeMail(String sentTo, String username, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your_email@gmail.com");
        message.setTo(sentTo);
        message.setSubject("Welcome");
        message.setText("Welcome to water meter services\n" +
                "Your credentials are as follows:\n\n" +
                "Username: " + username + "\nPassword: " + password);
        javaMailSender.send(message);
    }

    @Override
    public void sceduledUsedBill(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your_email@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    @Scheduled(cron = "0 0 9 * * *") // Every day at 9 AM
    public void sendDailyUsageEmails() {
        List<Household> households = householdRepo.findAll();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);

        for (Household household : households) {
            List<WaterUsage> usages = usageRepo.findByHousehold(household).stream()
                    .filter(u -> u.getDate().isAfter(yesterday) && u.getDate().isBefore(now))
                    .toList();

            double totalLiters = usages.stream().mapToDouble(WaterUsage::getLitersUsed).sum();
            double bill = totalLiters * 2.0; // Assuming ₹2 per liter

            String message = String.format("""
                Hello %s,

                Your water usage in the last 24 hours: %.2f liters
                Total bill amount: ₹%.2f

                Please ensure responsible water usage.

                Regards,
                Water Meter Team
                """, household.getOwnerName(), totalLiters, bill);

            if (household.getEmail() != null) {
                sceduledUsedBill(household.getEmail(), "Your Daily Water Usage Report", message);
            }
        }
    }

    @Scheduled(cron = "0 0 9 * * MON") // Every Monday at 9 AM
    public void sendWeeklyUsageEmails() {
        List<Household> households = householdRepo.findAll();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastWeek = now.minusDays(7);

        for (Household household : households) {
            List<WaterUsage> weeklyUsages = usageRepo.findByHousehold(household).stream()
                    .filter(u -> u.getDate().isAfter(lastWeek) && u.getDate().isBefore(now))
                    .toList();

            double totalLiters = weeklyUsages.stream().mapToDouble(WaterUsage::getLitersUsed).sum();
            double bill = totalLiters * 2.0;

            String message = String.format("""
            Hello %s,

            🗓 Weekly Water Usage Summary (Last 7 Days):
            Total Water Used: %.2f liters
            Total Bill Amount: ₹%.2f

            Let's continue saving water! 🌊

            Regards,
            Water Meter Team
            """, household.getOwnerName(), totalLiters, bill);

            if (household.getEmail() != null) {
                sceduledUsedBill(household.getEmail(), "Your Weekly Water Usage Summary", message);
            }
        }
    }
}
