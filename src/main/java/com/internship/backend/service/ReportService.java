package com.internship.backend.service;

import com.internship.backend.model.Report;
import com.internship.backend.model.User;
import com.internship.backend.repository.ReportRepository;
import com.internship.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Report> getAll() {
        return reportRepository.findAll();
    }

    public List<Report> getByStudentId(Long studentId) {
        return reportRepository.findByStudentId(studentId);
    }

    public Report submit(Report report, Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        report.setStudent(student);
        return reportRepository.save(report);
    }

    public Report grade(Long reportId, String grade, Long gradedById) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        User grader = userRepository.findById(gradedById)
                .orElseThrow(() -> new RuntimeException("Grader not found"));
        report.setGrade(grade);
        report.setGradedBy(grader);
        report.setGradedAt(LocalDateTime.now());
        return reportRepository.save(report);
    }
}
