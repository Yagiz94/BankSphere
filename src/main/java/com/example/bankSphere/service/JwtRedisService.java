// service/JwtRedisService.java
package com.example.bankSphere.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class JwtRedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * Save a JWT token in Redis with a specified TTL (in minutes).
     */
    public void saveToken(String token, String username, long ttlMinutes) {
        // Use token as key and username (or any identifier) as value
        redisTemplate.opsForValue().set(token, username, ttlMinutes, TimeUnit.MINUTES);
    }

    /**
     * Check if the token exists in Redis.
     */
    public boolean isTokenValid(String token) {
        return redisTemplate.hasKey(token);
    }

    /**
     * Remove a JWT token from Redis (e.g., during logout).
     */
    public void removeToken(String token) {
        redisTemplate.delete(token);
    }
}
