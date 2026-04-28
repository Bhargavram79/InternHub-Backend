package com.internship.backend.service;

import com.internship.backend.model.Announcement;
import com.internship.backend.model.User;
import com.internship.backend.repository.AnnouncementRepository;
import com.internship.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Announcement> getAll() {
        return announcementRepository.findAll();
    }

    public Announcement create(Announcement announcement, Long authorId) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        announcement.setAuthor(author);
        return announcementRepository.save(announcement);
    }

    public void delete(Long id) {
        announcementRepository.deleteById(id);
    }
}
