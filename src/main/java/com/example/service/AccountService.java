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

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public Optional<Account> findAccountById(Integer id) {
        return accountRepository.findById(id);
    }

    public Optional<Account> findAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

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
