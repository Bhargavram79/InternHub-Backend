package com.internship.backend.config;

import com.internship.backend.model.Internship;
import com.internship.backend.model.User;
import com.internship.backend.repository.InternshipRepository;
import com.internship.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InternshipRepository internshipRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            System.out.println("🌱 Seeding default data...");

            // === ADMIN ACCOUNTS ===
            User admin1 = createUser("ADM-001", "Bhargav Ram", "bhargav@internhub.com", "admin123", "ADMIN");
            User admin2 = createUser("ADM-002", "Rajesh Kumar", "rajesh@internhub.com", "admin123", "ADMIN");
            User admin3 = createUser("ADM-003", "Sunita Reddy", "sunita@internhub.com", "admin123", "ADMIN");

            // === STUDENT ACCOUNTS ===
            String[][] students = {
                    { "STU-001", "Priya Sharma", "priya@student.com" },
                    { "STU-002", "Rahul Verma", "rahul@student.com" },
                    { "STU-003", "Sneha Patil", "sneha@student.com" },
                    { "STU-004", "Vikram Singh", "vikram@student.com" },
                    { "STU-005", "Deepa Nair", "deepa@student.com" },
                    { "STU-006", "Karthik Iyer", "karthik@student.com" },
                    { "STU-007", "Meera Joshi", "meera@student.com" },
                    { "STU-008", "Arun Das", "arun@student.com" },
                    { "STU-009", "Lakshmi Menon", "lakshmi@student.com" },
                    { "STU-010", "Sanjay Gupta", "sanjay@student.com" },
                    { "STU-011", "Ananya Bose", "ananya@student.com" },
                    { "STU-012", "Rohan Deshmukh", "rohan@student.com" },
                    { "STU-013", "Pooja Kulkarni", "pooja@student.com" },
                    { "STU-014", "Arjun Rao", "arjun@student.com" },
                    { "STU-015", "Nisha Tiwari", "nisha@student.com" },
                    { "STU-016", "Riya Kapoor", "riya@student.com" },
                    { "STU-017", "Amit Jain", "amit@student.com" },
                    { "STU-018", "Kavya Pillai", "kavya@student.com" },
                    { "STU-019", "Suresh Yadav", "suresh@student.com" },
                    { "STU-020", "Harsh Tiwari", "harsh@student.com" },
            };

            for (String[] s : students) {
                createUser(s[0], s[1], s[2], "student123", "STUDENT");
            }

            // === INTERNSHIPS ===
            String[][] internships = {
                    { "Frontend Developer Intern", "Build responsive UIs using React", "TechCorp", "Hyderabad",
                            "Remote", "3 months", "Full-Time" },
                    { "Backend Developer Intern", "Develop REST APIs with Spring Boot", "DataSoft", "Bangalore",
                            "Hybrid", "6 months", "Full-Time" },
                    { "Data Analyst Intern", "Analyze datasets and create dashboards", "AnalyticsPro", "Mumbai",
                            "Remote", "3 months", "Part-Time" },
                    { "Mobile App Developer", "Build cross-platform apps using Flutter", "AppWorks", "Pune", "On-Site",
                            "4 months", "Full-Time" },
                    { "UI/UX Design Intern", "Design user interfaces for web apps", "DesignHub", "Chennai", "Remote",
                            "3 months", "Part-Time" },
                    { "DevOps Intern", "Manage CI/CD pipelines and cloud infra", "CloudNine", "Hyderabad", "Hybrid",
                            "6 months", "Full-Time" },
                    { "ML Engineer Intern", "Build and train ML models", "AILabs", "Bangalore", "Remote", "4 months",
                            "Full-Time" },
                    { "QA Testing Intern", "Write and execute test cases", "TestFirst", "Noida", "On-Site", "3 months",
                            "Full-Time" },
                    { "Cloud Computing Intern", "Work with AWS/Azure services", "SkyCompute", "Hyderabad", "Remote",
                            "3 months", "Part-Time" },
                    { "Cybersecurity Intern", "Conduct security audits", "SecureNet", "Delhi", "On-Site", "6 months",
                            "Full-Time" },
            };

            for (String[] i : internships) {
                Internship internship = new Internship();
                internship.setTitle(i[0]);
                internship.setDescription(i[1]);
                internship.setCompany(i[2]);
                internship.setLocation(i[3]);
                internship.setMode(i[4]);
                internship.setDuration(i[5]);
                internship.setType(i[6]);
                internship.setStatus("OPEN");
                internship.setMaxApplicants(10);
                internship.setPostedBy(admin1);
                internshipRepository.save(internship);
            }

            System.out.println("✅ Seeded 3 admins, 20 students, and 10 internships!");
        } else {
            System.out.println("📦 Database already has data, skipping seed.");
        }
    }

    private User createUser(String userId, String name, String email, String rawPassword, String role) {
        User user = new User();
        user.setUserId(userId);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(role);
        return userRepository.save(user);
    }
}
