package com.internship.backend.service;

import com.internship.backend.model.Internship;
import com.internship.backend.repository.InternshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InternshipService {

    @Autowired
    private InternshipRepository internshipRepository;

    public List<Internship> getAll() {
        return internshipRepository.findAll();
    }

    public Internship getById(Long id) {
        return internshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Internship not found"));
    }

    public Internship create(Internship internship) {
        return internshipRepository.save(internship);
    }

    public Internship update(Long id, Internship updates) {
        Internship existing = getById(id);
        if (updates.getTitle() != null)
            existing.setTitle(updates.getTitle());
        if (updates.getDescription() != null)
            existing.setDescription(updates.getDescription());
        if (updates.getCompany() != null)
            existing.setCompany(updates.getCompany());
        if (updates.getLocation() != null)
            existing.setLocation(updates.getLocation());
        if (updates.getMode() != null)
            existing.setMode(updates.getMode());
        if (updates.getDuration() != null)
            existing.setDuration(updates.getDuration());
        if (updates.getType() != null)
            existing.setType(updates.getType());
        if (updates.getStatus() != null)
            existing.setStatus(updates.getStatus());
        if (updates.getMaxApplicants() != null)
            existing.setMaxApplicants(updates.getMaxApplicants());
        return internshipRepository.save(existing);
    }

    public void delete(Long id) {
        internshipRepository.deleteById(id);
    }
}
