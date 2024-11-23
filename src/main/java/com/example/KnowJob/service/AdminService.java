package com.example.KnowJob.service;

import com.example.KnowJob.model.User;
import com.example.KnowJob.model.UserRole;
import com.example.KnowJob.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    @PostConstruct
    public void createAdminAccount() {
        Optional<User> optionalAdmin = userRepository.findByRole(UserRole.ROLE_ADMIN);

        if (optionalAdmin.isEmpty()) {
            User admin = User.builder()
                    .username("Admin")
                    .email("knowjobadmin@gmail.com")
                    .password(new BCryptPasswordEncoder().encode("knowjobadmin"))
                    .role(UserRole.ROLE_ADMIN)
                    .build();

            userRepository.save(admin);
        }
    }

}
