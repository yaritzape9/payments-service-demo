package com.yaritza.payments_service_demo.model;

public enum PaymentState {
    PENDING,
    PROCESSING,
    SUCCESS,
    FAILED;

    public boolean canTransitionTo(PaymentState next) {
        return switch (this) {
            case PENDING    -> next == PROCESSING;
            case PROCESSING -> next == SUCCESS || next == FAILED;
            case SUCCESS, FAILED -> false;
        };
    }
}