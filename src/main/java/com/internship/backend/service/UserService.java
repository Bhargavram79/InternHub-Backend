package com.internship.backend.service;

import com.internship.backend.dto.LoginRequest;
import com.internship.backend.dto.LoginResponse;
import com.internship.backend.exception.InvalidPasswordException;
import com.internship.backend.exception.UserAlreadyExistsException;
import com.internship.backend.exception.UserNotFoundException;
import com.internship.backend.model.User;
import com.internship.backend.repository.UserRepository;
import com.internship.backend.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ REGISTER
    public User registerUser(User user) {
        String email = user.getEmail().trim();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null)
            user.setRole("STUDENT");

        return userRepository.save(user);
    }

    // ✅ LOGIN
    public LoginResponse loginUser(LoginRequest request) {
        String email = request.getEmail().trim();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        LoginResponse response = new LoginResponse();
        response.setMessage("Login successful");
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setToken(token);

        return response;
    }

    // ✅ FIND BY ID
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // ✅ FIND ALL
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // ✅ FIND STUDENTS ONLY
    public List<User> findStudents() {
        return userRepository.findAll().stream()
                .filter(u -> "STUDENT".equals(u.getRole()))
                .collect(Collectors.toList());
    }

    // ✅ DELETE
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    // ✅ UPDATE PROFILE
    public User updateProfile(Long id, Map<String, String> data) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (data.containsKey("name"))
            user.setName(data.get("name"));
        if (data.containsKey("email"))
            user.setEmail(data.get("email"));
        if (data.containsKey("phone"))
            user.setPhone(data.get("phone"));
        if (data.containsKey("department"))
            user.setDepartment(data.get("department"));
        return userRepository.save(user);
    }

    // ✅ CHANGE PASSWORD
    public void changePassword(Long id, String currentPassword, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new InvalidPasswordException("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    // ✅ SAVE USER DIRECTLY (for DataSeeder)
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public long count() {
        return userRepository.count();
    }
}