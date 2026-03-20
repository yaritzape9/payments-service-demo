package com.yaritza.payments_service_demo.service;

import com.yaritza.payments_service_demo.model.Payment;
import com.yaritza.payments_service_demo.model.PaymentState;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PaymentStateMachineService {

    private final Map<String, Payment> store = new ConcurrentHashMap<>();

    public Payment create(String currency, double amount) {
        Payment p = new Payment(currency, amount);
        store.put(p.getId(), p);
        return p;
    }

    public Optional<Payment> get(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<Payment> advance(String id, PaymentState targetState) {
        return get(id).map(p -> {
            if (!p.transition(targetState)) {
                throw new IllegalStateException(
                    "Cannot transition from " + p.getState() + " → " + targetState
                );
            }
            return p;
        });
    }
}