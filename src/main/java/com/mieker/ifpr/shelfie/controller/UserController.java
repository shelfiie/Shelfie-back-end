package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.dto.User.UpdateUserDTO;
import com.mieker.ifpr.shelfie.dto.User.UserDTO;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.exception.GlobalExceptionHandler;
import com.mieker.ifpr.shelfie.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

//    get all users
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

//    Update user
    @PutMapping("/{id}/update")
    public ResponseEntity<UpdateUserDTO> updateUser(@PathVariable("id") UUID id, @RequestBody UpdateUserDTO userUpdateDTO) throws ParseException, GlobalExceptionHandler {
        UpdateUserDTO updateUserDTO = userService.updateUser(id, userUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updateUserDTO);
    }

//    endpoint de consulta do próprio usuário
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser()  {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        User currentUser = (User) authentication.getPrincipal();
        System.out.println(currentUser);
        return ResponseEntity.ok(currentUser);
    }

//    para fazer o delete do usuário, mas só alterando seu status para disabled
    @PutMapping("/{id}/disable")
    public ResponseEntity<String> disableUser(@PathVariable UUID id) {
        try {
            UserDTO userDTO = userService.updateUserDisable(id);
            String message = String.format("User '%s' disabled successfully", userDTO.getName());
            return ResponseEntity.ok(message);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
