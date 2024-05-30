package br.edu.ies.aps8.config;

import br.edu.ies.aps8.model.Role;
import br.edu.ies.aps8.model.User;
import br.edu.ies.aps8.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SetupComponent {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${application.default-password}")
    private String defaultPassword;

    @PostConstruct
    public void setup() {
        setupUser();
    }

    private void setupUser() {
        if (!userRepository.findByRoles(Role.ADMIN).isEmpty()) {
            return;
        }
        String encodedPassword = passwordEncoder.encode(defaultPassword);
        userRepository.save(User.builder()
                .username("admin")
                .password(encodedPassword)
                .roles(List.of(Role.ADMIN))
                .build());
    }

}