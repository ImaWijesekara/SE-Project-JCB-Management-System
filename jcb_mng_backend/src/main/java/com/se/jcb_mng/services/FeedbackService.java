package com.se.jcb_mng.services;

import com.se.jcb_mng.entities.Feedback;
import com.se.jcb_mng.entities.User;
import com.se.jcb_mng.repositories.FeedbackRepository;
import com.se.jcb_mng.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    public FeedbackService(FeedbackRepository feedbackRepository, UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
    }

    public Feedback submitFeedback(Long userId, String message, Integer rating) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setMessage(message);
        feedback.setRating(rating);

        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public List<Feedback> getFeedbackByUserId(Long userId) {
        return feedbackRepository.findByUserId(userId);
    }
}
