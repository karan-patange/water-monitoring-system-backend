package com.watermeter.usage.service;

public interface EmailService {


    public void WelcomeMail(String sentTo,String username, String password);

    public void sceduledUsedBill(String to, String subject, String text);
}
