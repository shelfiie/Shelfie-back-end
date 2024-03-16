package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.dto.DisableDTO;
import com.mieker.ifpr.shelfie.dto.RegisterUserDTO;
import com.mieker.ifpr.shelfie.dto.UpdateUserDTO;
import com.mieker.ifpr.shelfie.dto.UserDTO;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.entity.enumeration.UserRoles;
import com.mieker.ifpr.shelfie.mapper.UserMapper;
import com.mieker.ifpr.shelfie.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
// user controller -> service -> repository -> dto
// 

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    //
//    TODO
//    Olha isso aqui


//    criando um user
//    public User createUser(RegisterUserDTO registerDTO) throws ParseException {
//        User user = userMapper.signUpToUser(registerDTO);
//        return userRepository.save(user);
//    }

//    criar um user admin
    public User createAdministrator(RegisterUserDTO input) {
        User user = new User();
        user.setName(input.getName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setUserName(input.getUserName());
        user.setRole(UserRoles.ROLE_ADMIN);
        return userRepository.save(user);
    }

    public User updateUser(UUID id, UpdateUserDTO userUpdateDTO) throws ParseException {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        userToUpdate = userMapper.updateUserFromDTO(userToUpdate, userUpdateDTO);
        return userRepository.save(userToUpdate);
    }

    public User updateUserDisable(UUID id, DisableDTO disableDTO) throws ParseException {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        System.out.println(id);
        userToUpdate = userMapper.updateUserDisabled(userToUpdate, disableDTO);
        return userRepository.save(userToUpdate);
    }

    public User getUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
//        o stream faz uma sequencia dos objetos retornados para que cada objeto seja mapeado individualmente
        return users.stream()
//                referências de método
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }


    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }



}
