package com.yaritza.payments_service_demo.controller;

import com.yaritza.payments_service_demo.model.Payment;
import com.yaritza.payments_service_demo.model.PaymentState;
import com.yaritza.payments_service_demo.service.PaymentStateMachineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment/state")
public class PaymentStateMachineController {

    private final PaymentStateMachineService svc;

    public PaymentStateMachineController(PaymentStateMachineService svc) {
        this.svc = svc;
    }

    @PostMapping("/create")
    public ResponseEntity<Payment> create(@RequestBody Map<String, Object> body) {
        String currency = (String) body.get("currency");
        double amount   = ((Number) body.get("amount")).doubleValue();
        return ResponseEntity.ok(svc.create(currency, amount));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> get(@PathVariable String id) {
        return svc.get(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/advance")
    public ResponseEntity<?> advance(
        @PathVariable String id,
        @RequestBody Map<String, String> body
    ) {
        try {
            PaymentState target = PaymentState.valueOf(body.get("state"));
            return svc.advance(id, target)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid state value"));
        }
    }
}