package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.dto.User.RegisterUserDTO;
import com.mieker.ifpr.shelfie.dto.User.UpdateUserDTO;
import com.mieker.ifpr.shelfie.dto.User.UserDTO;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.entity.enumeration.UserRoles;
import com.mieker.ifpr.shelfie.exception.GlobalExceptionHandler;
import com.mieker.ifpr.shelfie.exception.IdNotFoundException;
import com.mieker.ifpr.shelfie.mapper.UserMapper;
import com.mieker.ifpr.shelfie.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
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

//    criar um user admin
    public User createAdministrator(RegisterUserDTO input) {
        User user = new User();
        user.setName(input.getName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setNickname(input.getNickname());
        user.setRole(UserRoles.ROLE_ADMIN);
        return userRepository.save(user);
    }

//    update do user
    public UpdateUserDTO updateUser(UUID id, UpdateUserDTO userUpdateDTO) throws ParseException, GlobalExceptionHandler {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        userToUpdate.setNickname(userUpdateDTO.getNickname());
        userToUpdate.setName(userUpdateDTO.getName());
        userRepository.save(userToUpdate);
        return userMapper.userToUpdateUserDTO(userToUpdate);
    }

//    atualizar o usuário para disable
    public String updateUserDisable(UUID id) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("User not found with id: " + id));
        userToUpdate.setEnabled(false);
        userRepository.save(userToUpdate);
        System.out.println(userToUpdate.getEnabled());
        return "Usuário desativado com sucesso";
    }

//    pegar o usuário pelo id
    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return userMapper.userToUserDTO(user);
    }

//    pegar todos os usuários
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
//        o stream faz uma sequencia dos objetos retornados para que cada objeto seja mapeado individualmente
        return users.stream()
//                referências de método
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }
}
