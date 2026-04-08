package com.internship.backend.controller;

import com.internship.backend.model.Feedback;
import com.internship.backend.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(Map.of("data", feedbackService.getAll()));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(Map.of("data", feedbackService.getByStudentId(studentId)));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> body) {
        Feedback feedback = new Feedback();
        feedback.setContent((String) body.get("content"));
        if (body.get("rating") != null) {
            feedback.setRating(((Number) body.get("rating")).intValue());
        }
        Long mentorId = ((Number) body.get("mentorId")).longValue();
        Long studentId = ((Number) body.get("studentId")).longValue();
        Long internshipId = body.get("internshipId") != null ? ((Number) body.get("internshipId")).longValue() : null;
        return ResponseEntity.ok(Map.of("data", feedbackService.create(feedback, mentorId, studentId, internshipId)));
    }
}
