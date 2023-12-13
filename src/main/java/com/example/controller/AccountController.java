package com.example.controller;

import com.example.entity.Account;
import com.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        // if invalid inputs, return 400, Client Error
        if (accountService.isValidRegistrationInput(account)) {
            return ResponseEntity.badRequest().build();
        }

        // if account exists return 409, Conflict
        if (accountService.findAccountByUsername(account.getUsername()).isPresent()) {
            return ResponseEntity.status(409).build();
        }

        // else save and return 200
        Account savedAccount = accountService.saveAccount(account);
        return ResponseEntity.ok(savedAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        // find the account by username
        return accountService.findAccountByUsername(account.getUsername())
                // check if the account has same password
                .filter(a -> a.getPassword().equals(account.getPassword()))
                // then return 200 and mapped account
                .map(a -> ResponseEntity.ok(a))
                // else 401 Unauthorized
                .orElse(ResponseEntity.status(401).build());
    }

}
