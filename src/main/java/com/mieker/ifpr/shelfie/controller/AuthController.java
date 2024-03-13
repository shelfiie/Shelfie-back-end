
package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.LoginDTO;
import com.mieker.ifpr.shelfie.dto.RegisterDTO;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.responses.LoginResponse;
import com.mieker.ifpr.shelfie.service.AuthenticationService;
import com.mieker.ifpr.shelfie.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.text.ParseException;

//@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterDTO signUpDTO) throws LoginException {
        try {
            User registeredUser = authenticationService.signUp(signUpDTO);
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDTO loginDTO) {
        try {
            User authenticatedUser = authenticationService.authenticate(loginDTO);
            String jwtToken = jwtService.generateToken(authenticatedUser);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtToken);
            loginResponse.setExpiresIn(jwtService.getExpirationTime());
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

