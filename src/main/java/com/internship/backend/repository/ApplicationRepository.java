package com.internship.backend.repository;

import com.internship.backend.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStudentId(Long studentId);

    boolean existsByStudentIdAndInternshipId(Long studentId, Long internshipId);
}
