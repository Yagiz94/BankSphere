package com.example.bankSphere.repository;

import com.example.bankSphere.entity.UserSecretKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSecretKeyRepository extends JpaRepository<UserSecretKey, Long> {
    // Find by username (to retrieve the secret key for the given user)
    Optional<UserSecretKey> findByUsername(String username);
}
