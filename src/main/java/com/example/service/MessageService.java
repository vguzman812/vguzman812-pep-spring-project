package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message saveAccount(Message message) {
        return messageRepository.save(message);
    }

    public Optional<Message> findmessageById(Integer id) {
        return messageRepository.findById(id);
    }

    public List<Message> findMessageByPostedBy(Integer id) {
        return messageRepository.findByPostedBy(id);
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public int deleteMessage(Integer id) {
        try {
            messageRepository.deleteById(id);
            return 1;
        } catch (EmptyResultDataAccessException e) {
            // message not found, so we get above exception and transaction does not occur.
            return 0;
        }
    }

    public int updateMessageText(Integer id, String newText) {
        // validate text.
        if (newText == null || newText.isBlank() || newText.length() > 255) {
            return 0;
        }
        // get the message object from db
        return messageRepository.findById(id)
                .map(message -> {
                    message.setMessage_text(newText);
                    messageRepository.save(message);
                    return 1;
                }).orElse(0);
    }

}
