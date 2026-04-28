package com.internship.backend.service;

import com.internship.backend.model.Feedback;
import com.internship.backend.model.User;
import com.internship.backend.model.Internship;
import com.internship.backend.repository.FeedbackRepository;
import com.internship.backend.repository.UserRepository;
import com.internship.backend.repository.InternshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InternshipRepository internshipRepository;

    public List<Feedback> getAll() {
        return feedbackRepository.findAll();
    }

    public List<Feedback> getByStudentId(Long studentId) {
        return feedbackRepository.findByStudentId(studentId);
    }

    public Feedback create(Feedback feedback, Long mentorId, Long studentId, Long internshipId) {
        User mentor = userRepository.findById(mentorId)
                .orElseThrow(() -> new RuntimeException("Mentor not found"));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        feedback.setMentor(mentor);
        feedback.setStudent(student);
        if (internshipId != null) {
            Internship internship = internshipRepository.findById(internshipId)
                    .orElseThrow(() -> new RuntimeException("Internship not found"));
            feedback.setInternship(internship);
        }
        return feedbackRepository.save(feedback);
    }
}
