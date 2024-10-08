package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.User.RegisterUserDTO;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*")
@RequestMapping("/api/admin")
@RestController
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    pra reforçar que o usuário precisa ser um admin para ter acesso a essa rota
    public ResponseEntity<User> createAdmin(@RequestBody RegisterUserDTO registerUserDTO) {
        User createAdmin = userService.createAdministrator(registerUserDTO);

        return ResponseEntity.status(201).body(createAdmin);
    }

    @PutMapping("/change-role/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    pra reforçar que o usuário precisa ser um admin para ter acesso a essa rota
    public ResponseEntity<String> changeRole(@PathVariable UUID userId) {
        String message = userService.changeRole(userId);

        return ResponseEntity.status(200).body(message);
    }
}