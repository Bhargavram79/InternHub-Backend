package com.internship.backend.service;

import com.internship.backend.model.*;
import com.internship.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InternshipRepository internshipRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private TaskRepository taskRepository;

    public List<Certificate> getAll() {
        return certificateRepository.findAll();
    }

    public List<Certificate> getByStudentId(Long studentId) {
        return certificateRepository.findByStudentId(studentId);
    }

    public Certificate issue(Long studentId, Long internshipId, String grade) {
        if (certificateRepository.existsByStudentIdAndInternshipId(studentId, internshipId)) {
            throw new RuntimeException("Certificate already issued for this student and internship");
        }
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Internship internship = internshipRepository.findById(internshipId)
                .orElseThrow(() -> new RuntimeException("Internship not found"));

        Certificate cert = new Certificate();
        cert.setStudent(student);
        cert.setInternship(internship);
        cert.setGrade(grade);
        return certificateRepository.save(cert);
    }

    // Get students eligible for certificate (accepted application + all tasks
    // completed)
    public List<User> getEligible() {
        List<Application> accepted = applicationRepository.findAll().stream()
                .filter(a -> "ACCEPTED".equals(a.getStatus()))
                .collect(Collectors.toList());

        return accepted.stream()
                .map(Application::getStudent)
                .filter(student -> {
                    List<Task> tasks = taskRepository.findByAssignedToId(student.getId());
                    return !tasks.isEmpty() && tasks.stream().allMatch(t -> "COMPLETED".equals(t.getStatus()));
                })
                .filter(student -> certificateRepository.findByStudentId(student.getId()).isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }
}
