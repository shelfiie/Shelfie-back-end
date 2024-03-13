package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.RegisterUserDTO;
import com.mieker.ifpr.shelfie.dto.UpdateUserDTO;
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
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private final UserService userService;

//    Admin endpoints

    //    criar usuário
//    @PostMapping
//    public ResponseEntity<User> createUser(@RequestBody RegisterUserDTO registerDTO) {
//        try {
//            User user = userService.createUser(registerDTO);
//            return ResponseEntity.status(HttpStatus.CREATED).body(user);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//    }


//    get user by id
    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        User user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

//    get all users
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

//    Update user
    @PutMapping("{id}")
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

//    Delete user
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") UUID id) {
        userService.deleteUser(id);
        ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok("User deleted successfully");
    }

    ////////////////////////////////////////////////////////////////////////
//    Readers endpoints

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<User> AuthenticatedUser()  {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }
}
