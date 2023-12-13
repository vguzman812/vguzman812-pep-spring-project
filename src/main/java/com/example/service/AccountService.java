package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Saves an account to the repository.
     * If the account is new, it will be created. If it already exists, it will be
     * updated.
     *
     * @param account The account to be saved.
     * @return The saved account, including its generated ID if it is new.
     */
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    /**
     * Finds an account by its ID.
     *
     * @param id The ID of the account to find.
     * @return An Optional containing the account if found, or an empty Optional
     *         otherwise.
     */
    public Optional<Account> findAccountById(Integer id) {
        return accountRepository.findById(id);
    }

    /**
     * Finds an account by its username.
     *
     * @param username The username of the account to find.
     * @return An Optional containing the account if found, or an empty Optional
     *         otherwise.
     */
    public Optional<Account> findAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    /**
     * Validates the input for account registration.
     * Checks if the username and password are not null, not blank, and if the
     * password meets the minimum length requirement.
     *
     * @param account The account to validate.
     * @return true if the input is valid, false otherwise.
     */
    public boolean isValidRegistrationInput(Account account) {
        if (account.getUsername() == null
                || account.getUsername().isBlank()
                || account.getPassword() == null
                || account.getPassword().isBlank()
                || account.getPassword().length() < 4) {
            return false;
        } else {
            return true;
        }
    }

}
