package com.example.repository;

import com.example.entity.Account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    /**
     * Find an account by its username.
     * 
     * @param username the username to search for.
     * @return an Optional containing the found Account, or an empty Optional if no
     *         account is found.
     */
    Optional<Account> findByUsername(String username);
}
