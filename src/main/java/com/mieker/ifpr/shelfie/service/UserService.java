package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.dto.RegisterDTO;
import com.mieker.ifpr.shelfie.dto.UpdateUserDTO;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.mapper.UserMapper;
import com.mieker.ifpr.shelfie.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
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
//    @Autowired
//    private ConfigMapper mapper;

//    @Autowired
//    private ConfigMapper modelMapper;

//    public User createUser(User user) {
//        return userRepository.save(user);
//    }

//    criando um userDto
    public User createUser(RegisterDTO registerDTO) throws ParseException {
        User user = userMapper.signUpToUser(registerDTO);
//        user = convertUserRegistration(registerDTO);
        return userRepository.save(user);
    }

    public User updateUser(UUID id, UpdateUserDTO userUpdateDTO) throws ParseException {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        userToUpdate = userMapper.updateUserFromDTO(userToUpdate, userUpdateDTO);
//        userToUpdate = this.userMapper.updateUser(userUpdateDTO);
        return userRepository.save(userToUpdate);
    }

//    private User convertUserRegistration(RegisterDTO signUpDTO) throws ParseException{
//        ConfigMapper mapper = new ConfigMapper();
////        User user = new User();
//        return mapper.map(signUpDTO, User.class);
////         user;
//    }

    public User getUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


//    public User updateUser(User user) {
//        User existingUser = userRepository.findById(user.getId()).get();
//        existingUser.setName(user.getName());
//        existingUser.setImage(user.getImage());
//        existingUser.setUsername(user.getUsername());
//        return userRepository.save(existingUser);
//    }
//
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }



}
