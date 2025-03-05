package com.example.bankSphere.enums;

public enum ROLE {
    PERSONAL_USER(0),
    CORPORATE_USER(1),
    ADMIN(2);

    private final int value;

    ROLE(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
