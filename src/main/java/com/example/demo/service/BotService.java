package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class BotService {
    public String getResponse(String messageBody) {
        switch (messageBody.toLowerCase()) {
            case "hello":
                return "Hi there! How can I help you today?";
            case "hours":
                return "Our working hours are from 9 AM to 5 PM, Monday to Friday.";
            case "contact":
                return "You can contact us at contact@example.com.";
            default:
                return "Sorry, I didn't understand that. Can you please rephrase?";
        }
    }
}
