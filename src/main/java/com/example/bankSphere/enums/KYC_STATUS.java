package com.example.bankSphere.enums;

public enum KYC_STATUS {
    PENDING(0),
    VERIFIED(1),
    REJECTED(2);

    private final int value;

    KYC_STATUS(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}