package com.se.jcb_mng.controllers;

import com.se.jcb_mng.entities.Feedback;
import com.se.jcb_mng.services.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/submit")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> submitFeedback(
            @RequestParam Long userId,
            @RequestParam String message,
            @RequestParam Integer rating) {
        try {
            Feedback feedback = feedbackService.submitFeedback(userId, message, rating);
            return ResponseEntity.ok(feedback);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')") // Only Admins can see all feedback
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        return ResponseEntity.ok(feedbackService.getAllFeedback());
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')") // Admins or Customers can view user-specific feedback
    public ResponseEntity<List<Feedback>> getFeedbackByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(feedbackService.getFeedbackByUserId(userId));
    }

}
