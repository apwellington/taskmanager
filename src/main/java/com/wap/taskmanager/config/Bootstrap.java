package com.wap.taskmanager.config;

import com.wap.taskmanager.entity.User;
import com.wap.taskmanager.repository.UserRepository;
import com.wap.taskmanager.util.UserRole;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Bootstrap implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Bootstrap(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.userRepository.save(new User(1L, "admin", passwordEncoder.encode("root"), UserRole.ADMIN));
    }
}
