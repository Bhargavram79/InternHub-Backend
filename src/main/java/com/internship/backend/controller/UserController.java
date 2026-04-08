package com.internship.backend.controller;

import com.internship.backend.dto.LoginRequest;
import com.internship.backend.dto.LoginResponse;
import com.internship.backend.model.User;
import com.internship.backend.security.TokenBlacklist;
import com.internship.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenBlacklist tokenBlacklist;

    // ✅ REGISTER (auto-login: returns JWT token + user data)
    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> body) {
        try {
            // Register the user
            User user = new User();
            user.setName(body.get("name"));
            user.setEmail(body.get("email"));
            user.setPassword(body.get("password"));
            user.setRole(body.getOrDefault("role", "STUDENT"));
            User created = userService.registerUser(user);

            // Auto-login: generate JWT token and return LoginResponse
            LoginRequest loginReq = new LoginRequest();
            loginReq.setEmail(body.get("email"));
            loginReq.setPassword(body.get("password"));
            LoginResponse response = userService.loginUser(loginReq);

            return ResponseEntity.ok(Map.of("data", response));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // ✅ LOGIN
    @PostMapping("/auth/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = userService.loginUser(request);
            return ResponseEntity.ok(Map.of("data", response));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // ✅ VALIDATE USER (for session validation)
    @GetMapping("/auth/validate/{id}")
    public ResponseEntity<?> validateUser(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(Map.of("data", user)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ GET ALL USERS
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(Map.of("data", userService.findAll()));
    }

    // ✅ GET STUDENTS ONLY
    @GetMapping("/users/students")
    public ResponseEntity<?> getStudents() {
        return ResponseEntity.ok(Map.of("data", userService.findStudents()));
    }

    // ✅ CREATE USER (admin)
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> body) {
        try {
            User user = new User();
            user.setName(body.get("name"));
            user.setEmail(body.get("email"));
            user.setPassword(body.get("password"));
            user.setRole(body.getOrDefault("role", "STUDENT"));
            User created = userService.registerUser(user);
            return ResponseEntity.ok(Map.of("data", created));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // ✅ DELETE USER
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "User deleted"));
    }

    // ✅ UPDATE PROFILE
    @PutMapping("/users/{id}/profile")
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody Map<String, String> body) {
        User updated = userService.updateProfile(id, body);
        return ResponseEntity.ok(Map.of("data", updated));
    }

    // ✅ CHANGE PASSWORD
    @PutMapping("/users/{id}/password")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            userService.changePassword(id, body.get("currentPassword"), body.get("newPassword"));
            return ResponseEntity.ok(Map.of("message", "Password changed"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // ✅ LOGOUT (BLACKLIST TOKEN)
    @PostMapping("/users/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlacklist.blacklistToken(token);
        }
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
}