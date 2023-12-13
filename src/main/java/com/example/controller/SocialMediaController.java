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

    /**
     * Registers a new user account.
     * 
     * @param account The account details for registration.
     * @return ResponseEntity with the created Account or an error status: 400, 409.
     */
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

    /**
     * Authenticates a user based on username and password.
     * 
     * @param account The account details for login.
     * @return ResponseEntity with the authenticated Account or an unauthorized 401
     *         error status.
     */
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        return accountService.findAccountByUsername(account.getUsername())
                .filter(a -> a.getPassword().equals(account.getPassword()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    /**
     * Creates a new message.
     * 
     * @param message The message details to be saved.
     * @return ResponseEntity with the created Message or a bad request error
     *         status 400.
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        if (!messageService.isValidMessageText(message)) {
            String msg = "Oh no, I'm in side a bad dthing.";
            return ResponseEntity.badRequest().build();
        }
        Message savedMessage = messageService.saveAccount(message);
        return ResponseEntity.ok(savedMessage);
    }

    /**
     * Retrieves all messages.
     * 
     * @return ResponseEntity with a list of Messages.
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.findAll();
        return ResponseEntity.ok(messages);
    }

    /**
     * Retrieves a specific message by its ID.
     * 
     * @param id The ID of the message to retrieve.
     * @return ResponseEntity with the requested Message or an empty response if not
     *         found.
     */
    @GetMapping("/messages/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer id) {
        return messageService.findmessageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok().build());
    }

    /**
     * Deletes a message by its ID.
     * 
     * @param id The ID of the message to be deleted.
     * @return ResponseEntity with the number of rows affected or no body.
     */
    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer id) {
        int result = messageService.deleteMessage(id);
        return result == 1 ? ResponseEntity.ok(result) : ResponseEntity.ok().build();
    }

    /**
     * Updates the text of an existing message.
     * 
     * @param id      The ID of the message to be updated.
     * @param message The updated message details.
     * @return ResponseEntity with the nmber of rows affected or error status of 400
     */
    @PatchMapping("/messages/{id}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer id, @RequestBody Message message) {
        int result = messageService.updateMessageText(id, message.getMessage_text());
        return result == 1 ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }

    /**
     * Retrieves all messages posted by a specific user.
     * 
     * @param accountId The ID of the user whose messages are to be retrieved.
     * @return ResponseEntity with a list of Messages posted by the specified user.
     */
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable Integer accountId) {
        List<Message> messages = messageService.findMessageByPostedBy(accountId);
        return ResponseEntity.ok(messages);
    }

}
