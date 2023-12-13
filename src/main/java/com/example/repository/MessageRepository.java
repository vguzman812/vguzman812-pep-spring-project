package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    /**
     * Find all messages posted by a specific user using a custom query.
     * 
     * @param id The ID of the user who posted the messages.
     * @return A list of messages.
     */
    @Query("SELECT m FROM Message m WHERE m.posted_by = :id")
    List<Message> findByPostedBy(Integer id);
}
