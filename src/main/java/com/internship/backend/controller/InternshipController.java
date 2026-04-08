package com.internship.backend.controller;

import com.internship.backend.model.Internship;
import com.internship.backend.model.User;
import com.internship.backend.service.InternshipService;
import com.internship.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/internships")
public class InternshipController {

    @Autowired
    private InternshipService internshipService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(Map.of("data", internshipService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of("data", internshipService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> body) {
        Internship internship = new Internship();
        internship.setTitle((String) body.get("title"));
        internship.setDescription((String) body.get("description"));
        internship.setCompany((String) body.get("company"));
        internship.setLocation((String) body.get("location"));
        internship.setMode((String) body.get("mode"));
        internship.setDuration((String) body.get("duration"));
        internship.setType((String) body.get("type"));
        internship.setStatus((String) body.getOrDefault("status", "OPEN"));
        if (body.get("maxApplicants") != null) {
            internship.setMaxApplicants(((Number) body.get("maxApplicants")).intValue());
        }
        if (body.get("postedById") != null) {
            Long postedById = ((Number) body.get("postedById")).longValue();
            userRepository.findById(postedById).ifPresent(internship::setPostedBy);
        }
        return ResponseEntity.ok(Map.of("data", internshipService.create(internship)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Internship updates) {
        return ResponseEntity.ok(Map.of("data", internshipService.update(id, updates)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        internshipService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Deleted"));
    }
}
