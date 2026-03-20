package com.yaritza.payments_service_demo.model;

import java.time.Instant;
import java.util.UUID;

public class Payment {
    private final String id;
    private PaymentState state;
    private final String currency;
    private final double amount;
    private Instant updatedAt;

    public Payment(String currency, double amount) {
        this.id        = UUID.randomUUID().toString();
        this.state     = PaymentState.PENDING;
        this.currency  = currency;
        this.amount    = amount;
        this.updatedAt = Instant.now();
    }

    public boolean transition(PaymentState next) {
        if (!state.canTransitionTo(next)) return false;
        this.state     = next;
        this.updatedAt = Instant.now();
        return true;
    }

    public String getId()          { return id; }
    public PaymentState getState() { return state; }
    public String getCurrency()    { return currency; }
    public double getAmount()      { return amount; }
    public Instant getUpdatedAt()  { return updatedAt; }
}