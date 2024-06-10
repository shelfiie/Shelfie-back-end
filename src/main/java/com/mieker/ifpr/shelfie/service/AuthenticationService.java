package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.config.Validation;
import com.mieker.ifpr.shelfie.dto.User.LoginDTO;
import com.mieker.ifpr.shelfie.dto.User.RegisterUserDTO;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.entity.enumeration.UserRoles;
import com.mieker.ifpr.shelfie.mapper.UserMapper;
import com.mieker.ifpr.shelfie.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.regex.Pattern;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;


    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
    }



    public User signUp (RegisterUserDTO input) throws LoginException {

        if (userRepository.existsByEmail(input.getEmail())) {
            throw new LoginException("Email já cadastrado.");
        }

        if (userRepository.existsByUsernome(input.getUsernome())) {
            throw new LoginException("Nome de usuário já cadastrado.");
        }

        if (!Validation.emailValidation(input.getEmail())) {
            throw new LoginException("Email inválido.");
        }

        User user = new User();
        user.setName(input.getName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setUsernome(input.getUsernome());
        user.setRole(UserRoles.ROLE_READER);
        return userRepository.save(user);
    }

    public User authenticate (LoginDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }
}
