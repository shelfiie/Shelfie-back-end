package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.DisableDTO;
import com.mieker.ifpr.shelfie.dto.RegisterUserDTO;
import com.mieker.ifpr.shelfie.dto.UpdateUserDTO;
import com.mieker.ifpr.shelfie.dto.UserDTO;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//TODO
// olhar isso aqui dps o crossorigins
//@CrossOrigin(origins = "http://localhost:8080")

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
//@CrossOrigin(origins = "http://172.16.68.188:5173")

public class UserController {
    @Autowired
    private final UserService userService;

//    Admin endpoints

//    TODO
//    arrumar esse para retornar um DTO
//    get user by id
    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        User user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/hi")
    public ResponseEntity<String> hi() {

        return ResponseEntity.ok("hiii-com preauthorize");
    }

//    @PreAuthorize("isAuthenticated()")
//    @CrossOrigi/n(origins = "*")
    @GetMapping("/hii")
    public ResponseEntity<String> hii() {

        return ResponseEntity.ok("hiii-sem preauthorize");
    }

//    get all users
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

//    TODO
//    arrumar isso aqui para retornar um DTO
//    Update user
    @PutMapping("update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") UUID id, @RequestBody UpdateUserDTO userUpdateDTO) {
        try {
            User user = userService.updateUser(id, userUpdateDTO);
            System.out.println(user);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    ////////////////////////////////////////////////////////////////////////
//    Readers endpoints
//todo
//    paginometro
//    @CrossOrigin(origins = "http://localhost:5173")
//    endpoint de consulta do próprio usuário
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<User> AuthenticatedUser()  {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        User currentUser = (User) authentication.getPrincipal();
        System.out.println(currentUser);
        return ResponseEntity.ok(currentUser);
    }

//    para fazer o delete do usuário, mas só alterando seu status para disabled
    @PutMapping("/{id}/disable")
    public ResponseEntity<String> deleteUser(@PathVariable("id") UUID id, @RequestBody DisableDTO disableDTO) {
        try {
            User user = userService.updateUserDisable(id, disableDTO);
            System.out.println(user);
            return ResponseEntity.ok("User disabled successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
