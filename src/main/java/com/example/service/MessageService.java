package com.example.service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
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

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Saves a message to the repository.
     * If the message is new, it will be created. If it already exists, it will be
     * updated.
     *
     * @param message The message to be saved.
     * @return The saved message, including its generated ID if it is new.
     */
    public Message saveAccount(Message message) {
        return messageRepository.save(message);
    }

    /**
     * Finds a message by its ID.
     *
     * @param id The ID of the message to find.
     * @return An Optional containing the message if found, or an empty Optional
     *         otherwise.
     */
    public Optional<Message> findmessageById(Integer id) {
        return messageRepository.findById(id);
    }

    /**
     * Retrieves all messages posted by a specific user.
     *
     * @param id The ID of the user whose messages are to be retrieved.
     * @return A list of messages posted by the specified user.
     */
    public List<Message> findMessageByPostedBy(Integer id) {
        return messageRepository.findByPostedBy(id);
    }

    /**
     * Retrieves all messages.
     *
     * @return A list of all messages.
     */
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    /**
     * Deletes a message by its ID.
     *
     * @param id The ID of the message to be deleted.
     * @return 1 if the message was successfully deleted, 0 if the message was not
     *         found.
     */
    public int deleteMessage(Integer id) {
        try {
            messageRepository.deleteById(id);
            return 1;
        } catch (EmptyResultDataAccessException e) {
            // message not found, so we get above exception and transaction does not occur.
            return 0;
        }
    }

    /**
     * Updates the text of an existing message.
     *
     * @param id      The ID of the message to be updated.
     * @param newText The new text to be set for the message.
     * @return 1 if the update was successful, 0 if not (e.g., invalid text or
     *         message not found).
     */
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

    /**
     * Validates the text of a message.
     * Checks that the message text is not null, not blank, within the character
     * limit, and that the poster exists.
     *
     * @param message The message to validate.
     * @return true if the message text is valid, false otherwise.
     */
    public boolean isValidMessageText(Message message) {
        if (message.getPosted_by() != null) {
            if (message.getMessage_text() == null
                    || message.getMessage_text().isBlank()
                    || message.getMessage_text().length() > 255
                    || !accountRepository.existsById(message.getPosted_by())) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

}
