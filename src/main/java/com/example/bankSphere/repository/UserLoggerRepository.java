package com.example.bankSphere.repository;

import com.example.bankSphere.entity.UserLogger;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserLoggerRepository extends MongoRepository<UserLogger, String> {
}
