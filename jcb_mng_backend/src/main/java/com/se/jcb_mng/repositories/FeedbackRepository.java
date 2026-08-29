package com.se.jcb_mng.repositories;

import com.se.jcb_mng.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    // Auto-generates SQL to get all feedback from a specific user
    List<Feedback> findByUserId(Long userId);


}
