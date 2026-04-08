package com.internship.backend.controller;

import com.internship.backend.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(Map.of("data", certificateService.getAll()));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(Map.of("data", certificateService.getByStudentId(studentId)));
    }

    @GetMapping("/eligible")
    public ResponseEntity<?> getEligible() {
        return ResponseEntity.ok(Map.of("data", certificateService.getEligible()));
    }

    @PostMapping
    public ResponseEntity<?> issue(@RequestBody Map<String, Object> body) {
        Long studentId = ((Number) body.get("studentId")).longValue();
        Long internshipId = ((Number) body.get("internshipId")).longValue();
        String grade = (String) body.get("grade");
        return ResponseEntity.ok(Map.of("data", certificateService.issue(studentId, internshipId, grade)));
    }
}
