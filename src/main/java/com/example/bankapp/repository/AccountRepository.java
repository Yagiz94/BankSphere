// repository/AccountRepository.java
package com.example.bankapp.repository;

import com.example.bankapp.entity.Account;
import com.example.bankapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUser(User user);
}
