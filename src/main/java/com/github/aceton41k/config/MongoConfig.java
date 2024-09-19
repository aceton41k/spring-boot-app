package com.github.aceton41k.config;

import com.github.aceton41k.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Optional;

@Configuration
@EnableMongoAuditing
public class MongoConfig {
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.empty(); // Если пользователь не аутентифицирован
            }

            // Предполагается, что principal является объектом вашего класса User
            Object principal = authentication.getPrincipal();

            if (principal instanceof User user) {
                return Optional.of(user.getId()); // Верните id пользователя
            } else {
                return Optional.empty();
            }
        };
    }
}