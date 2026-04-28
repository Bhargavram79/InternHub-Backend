package com.internship.backend.repository;

import com.internship.backend.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByStudentId(Long studentId);
}
