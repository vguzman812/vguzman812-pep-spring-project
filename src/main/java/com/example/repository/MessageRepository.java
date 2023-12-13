package com.example.repository;

import com.example.entity.Message;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    /**
     * Find all messages posted by a specific user.
     * @param id The ID of the user who posted the messages.
     * @return A list of messages.
     */
    List<Message> findByPostedBy(Integer id);

}
