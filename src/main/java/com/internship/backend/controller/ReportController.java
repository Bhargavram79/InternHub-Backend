package com.internship.backend.controller;

import com.internship.backend.model.Report;
import com.internship.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(Map.of("data", reportService.getAll()));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(Map.of("data", reportService.getByStudentId(studentId)));
    }

    @PostMapping
    public ResponseEntity<?> submit(@RequestBody Map<String, Object> body) {
        Report report = new Report();
        report.setTitle((String) body.get("title"));
        report.setContent((String) body.get("content"));
        report.setFileUrl((String) body.get("fileUrl"));
        Long studentId = ((Number) body.get("studentId")).longValue();
        return ResponseEntity.ok(Map.of("data", reportService.submit(report, studentId)));
    }

    @PutMapping("/{id}/grade")
    public ResponseEntity<?> grade(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        String grade = (String) body.get("grade");
        Long gradedById = ((Number) body.get("gradedById")).longValue();
        return ResponseEntity.ok(Map.of("data", reportService.grade(id, grade, gradedById)));
    }
}
