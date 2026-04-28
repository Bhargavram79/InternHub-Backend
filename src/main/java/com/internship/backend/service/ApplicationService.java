package com.internship.backend.service;

import com.internship.backend.model.Application;
import com.internship.backend.model.Internship;
import com.internship.backend.model.User;
import com.internship.backend.repository.ApplicationRepository;
import com.internship.backend.repository.InternshipRepository;
import com.internship.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InternshipRepository internshipRepository;

    public List<Application> getAll() {
        return applicationRepository.findAll();
    }

    public List<Application> getByStudentId(Long studentId) {
        return applicationRepository.findByStudentId(studentId);
    }

    public Application apply(Long studentId, Long internshipId) {
        if (applicationRepository.existsByStudentIdAndInternshipId(studentId, internshipId)) {
            throw new RuntimeException("Already applied to this internship");
        }
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Internship internship = internshipRepository.findById(internshipId)
                .orElseThrow(() -> new RuntimeException("Internship not found"));

        Application app = new Application();
        app.setStudent(student);
        app.setInternship(internship);
        return applicationRepository.save(app);
    }

    public Application updateStatus(Long id, String status) {
        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        app.setStatus(status);
        return applicationRepository.save(app);
    }
}
