package com.internship.backend.controller;

import com.internship.backend.model.Announcement;
import com.internship.backend.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(Map.of("data", announcementService.getAll()));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> body) {
        Announcement announcement = new Announcement();
        announcement.setTitle((String) body.get("title"));
        announcement.setContent((String) body.get("content"));
        Long authorId = ((Number) body.get("authorId")).longValue();
        return ResponseEntity.ok(Map.of("data", announcementService.create(announcement, authorId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        announcementService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Deleted"));
    }
}
