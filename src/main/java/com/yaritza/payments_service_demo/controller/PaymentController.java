package com.yaritza.payments_service_demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    @PostMapping("/retry")
    public ResponseEntity<?> simulateRetry(@RequestBody Map<String, String> request) {
        String amount = request.get("amount");
        String currency = request.get("currency");

        if (amount == null || currency == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing required fields: amount, currency"));
        }

        List<Map<String, String>> attempts = new ArrayList<>();
        String finalStatus = "failed";
        Random random = new Random();

        for (int i = 1; i <= 3; i++) {
            String outcome;
            if (i == 3) {
                outcome = "success";
            } else {
                outcome = random.nextBoolean() ? "declined" : "network_failure";
            }

            String message = switch (outcome) {
                case "success" -> "Payment of " + amount + " " + currency + " processed successfully";
                case "declined" -> "Card declined — retrying...";
                default -> "Network failure — retrying...";
            };

            attempts.add(Map.of("attempt", String.valueOf(i), "status", outcome, "message", message));

            if (outcome.equals("success")) {
                finalStatus = "success";
                break;
            }
        }

        return ResponseEntity.ok(Map.of("attempts", attempts, "finalStatus", finalStatus));
    }
}