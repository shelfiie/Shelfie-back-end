package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.config.Validation;
import com.mieker.ifpr.shelfie.dto.User.LoginDTO;
import com.mieker.ifpr.shelfie.dto.User.RegisterUserDTO;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.entity.enumeration.UserRoles;
import com.mieker.ifpr.shelfie.exception.NotFoundException;
import com.mieker.ifpr.shelfie.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signUp (RegisterUserDTO input) throws LoginException {

        if (userRepository.existsByEmail(input.getEmail())) {
            throw new LoginException("Email já cadastrado.");
        }

        if (userRepository.existsByNickname(input.getNickname())) {
            throw new LoginException("Nome de usuário já cadastrado.");
        }

        if (!Validation.emailValidation(input.getEmail())) {
            throw new LoginException("Email inválido.");
        }

        User user = new User();
        user.setName(input.getName());
        user.setEmail(input.getEmail());
        user.setImage("https://imgur.com/FTj8i7I.png");
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setNickname(input.getNickname());
        user.setRole(UserRoles.ROLE_READER);
        return userRepository.save(user);
    }

    public User authenticate (LoginDTO input) {

        User user = userRepository.findByEmail(input.getEmail()).orElseThrow();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        if (!user.getEnabled()) {
            throw new NotFoundException("Usuário desabilitado. Contate o administrador.");
        }

        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }
}
