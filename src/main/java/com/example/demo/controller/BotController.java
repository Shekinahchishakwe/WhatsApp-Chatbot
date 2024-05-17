package com.example.demo.controller;

import com.example.demo.service.BotService;
import com.example.demo.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/webhook")
public class BotController {

    @Autowired
    private BotService botService;

    @Value("${whatsapp.api.url}")
    private String apiUrl;

    @Value("${whatsapp.api.token}")
    private String apiToken;

    @PostMapping
    public void handleMessage(@RequestBody Message message) {
        String response = botService.getResponse(message.getBody());
        sendMessage(message.getFrom(), response);
    }

    @GetMapping("/test")
    public String testSendMessage(@RequestParam String to, @RequestParam String body) {
        sendMessage(to, body);
        return "Message sent!";
    }

    private void sendMessage(String to, String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);
        headers.set("Content-Type", "application/json");

        // Constructing the JSON payload with the recipient's number and message body
        String json = "{\"messaging_product\": \"whatsapp\", \"to\": \"" + to + "\", \"type\": \"text\", \"text\": {\"body\": \"" + body + "\"}}";

        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Message sent successfully");
            } else {
                System.out.println("Failed to send message: " + response.getBody());
            }
        } catch (Exception e) {
            System.out.println("Error occurred while sending message: " + e.getMessage());
        }
    }
}
