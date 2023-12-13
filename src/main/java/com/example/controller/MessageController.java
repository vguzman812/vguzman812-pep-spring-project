package com.example.controller;

import com.example.entity.Message;
import com.example.service.MessageService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        if (messageService.isValidMessageText(message)) {
            return ResponseEntity.badRequest().build();
        }
        Message savedMessage = messageService.saveAccount(message);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.findAll();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer id) {
        return messageService.findmessageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer id) {
        int result = messageService.deleteMessage(id);
        return result == 1 ? ResponseEntity.ok(result) : ResponseEntity.ok().build();
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer id, @RequestBody Message message) {
        int result = messageService.updateMessageText(id, message.getMessage_text());
        return result == 1 ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }


    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable Integer accountId) {
        List<Message> messages = messageService.findMessageByPostedBy(accountId);
        return ResponseEntity.ok(messages);
    }

}
