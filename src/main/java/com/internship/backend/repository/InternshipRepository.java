package com.internship.backend.repository;

import com.internship.backend.model.Internship;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InternshipRepository extends JpaRepository<Internship, Long> {
    Optional<Internship> findByTitle(String title);
}
