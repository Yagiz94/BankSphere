package com.example.bankSphere.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_logger")
public class UserLogger {
    @Id
    private String id;
    private String name;
    private String description;

    // No-argument constructor for frameworks
    public UserLogger(){}

    public UserLogger(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }






}
