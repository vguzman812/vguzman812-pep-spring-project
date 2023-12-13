package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        if (!accountService.isValidRegistrationInput(account)) {
            return ResponseEntity.badRequest().build();
        }

        if (accountService.findAccountByUsername(account.getUsername()).isPresent()) {
            return ResponseEntity.status(409).build();
        }

        Account savedAccount = accountService.saveAccount(account);
        return ResponseEntity.ok(savedAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        return accountService.findAccountByUsername(account.getUsername())
                .filter(a -> a.getPassword().equals(account.getPassword()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        if (!messageService.isValidMessageText(message)) {
            String msg = "Oh no, I'm in side a bad dthing.";
            return ResponseEntity.badRequest().build();
        }
        Message savedMessage = messageService.saveAccount(message);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.findAll();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer id) {
        return messageService.findmessageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok().build());
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer id) {
        int result = messageService.deleteMessage(id);
        return result == 1 ? ResponseEntity.ok(result) : ResponseEntity.ok().build();
    }

    @PatchMapping("/messages/{id}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer id, @RequestBody Message message) {
        int result = messageService.updateMessageText(id, message.getMessage_text());
        return result == 1 ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable Integer accountId) {
        List<Message> messages = messageService.findMessageByPostedBy(accountId);
        return ResponseEntity.ok(messages);
    }

}
