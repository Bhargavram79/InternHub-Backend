package com.internship.backend.service;

import com.internship.backend.model.Task;
import com.internship.backend.model.User;
import com.internship.backend.model.Internship;
import com.internship.backend.repository.TaskRepository;
import com.internship.backend.repository.UserRepository;
import com.internship.backend.repository.InternshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InternshipRepository internshipRepository;

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public List<Task> getByStudentId(Long studentId) {
        return taskRepository.findByAssignedToId(studentId);
    }

    public Task create(Task task, Long assignedToId, Long internshipId) {
        if (assignedToId != null) {
            User student = userRepository.findById(assignedToId)
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            task.setAssignedTo(student);
        }
        if (internshipId != null) {
            Internship internship = internshipRepository.findById(internshipId)
                    .orElseThrow(() -> new RuntimeException("Internship not found"));
            task.setInternship(internship);
        }
        return taskRepository.save(task);
    }

    public Task updateStatus(Long id, String status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(status);
        return taskRepository.save(task);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
