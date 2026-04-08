package com.internship.backend.repository;

import com.internship.backend.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    List<Certificate> findByStudentId(Long studentId);

    boolean existsByStudentIdAndInternshipId(Long studentId, Long internshipId);
}
