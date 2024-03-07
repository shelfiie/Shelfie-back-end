package com.mieker.ifpr.shelfie.controller;

import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.repository.UserRepository;
import com.mieker.ifpr.shelfie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO
// olhar isso aqui dps o crossorigins
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

//    criar usuário
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User newUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
//            return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
//        User newUser = userService.createUser(user);
//        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

//    get user by id
    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

//    get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

//    Update user
    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        try {
            user.setId(id);
            User updatedUser = userService.updateUser(user);
            System.out.println(updatedUser);
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
//        user.setId(id);
//        User updatedUser = userService.updateUser(user);
//        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

//    Delete user
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok("User deleted successfully");
    }
}
