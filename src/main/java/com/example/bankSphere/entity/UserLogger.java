package com.example.bankSphere.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_logger")
public class UserLogger {
    private String userId;
    private String name;
    private String description;

    public UserLogger(String userId, String name, String description) {
        this.userId = userId;
        this.name = name;
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
