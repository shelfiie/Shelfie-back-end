package com.mieker.ifpr.shelfie.bootstrap;

import com.mieker.ifpr.shelfie.dto.User.RegisterUserDTO;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.entity.enumeration.UserRoles;
import com.mieker.ifpr.shelfie.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.createAdministrator();
    }

    private void createAdministrator() {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        registerUserDTO.setName("Admin");
        registerUserDTO.setEmail("admin@g.com");
        registerUserDTO.setNickname("admin");
        registerUserDTO.setPassword("password");

        Optional<User> optionalUser = userRepository.findByEmail(registerUserDTO.getEmail());

        if (optionalUser.isPresent()) {
            return;
        }

        User user = new User();
        user.setName(registerUserDTO.getName());
        user.setEmail(registerUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        user.setNickname(registerUserDTO.getNickname());
        user.setRole(UserRoles.ROLE_ADMIN);
        user.setEnabled(true);

        userRepository.save(user);
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
