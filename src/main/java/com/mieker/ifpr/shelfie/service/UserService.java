package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.dto.SignUpDTO;
import com.mieker.ifpr.shelfie.dto.UpdateUserDTO;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.mapper.UserMapper;
import com.mieker.ifpr.shelfie.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
// user controller -> service -> repository -> dto
// 

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    //
//    TODO
//    Olha isso aqui


//    criando um userDto
    public User createUser(SignUpDTO signUpDTO) throws ParseException {
        User user = userMapper.signUpToUser(signUpDTO);
//        user = convertUserRegistration(signUpDTO);
        return userRepository.save(user);
    }

    public User updateUser(UUID id, UpdateUserDTO userUpdateDTO) throws ParseException {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        userToUpdate = userMapper.updateUserFromDTO(userToUpdate, userUpdateDTO);
        return userRepository.save(userToUpdate);
    }


    public User getUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }



}
