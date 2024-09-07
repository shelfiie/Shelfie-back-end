package com.mieker.ifpr.shelfie.service;

import com.mieker.ifpr.shelfie.config.Validation;
import com.mieker.ifpr.shelfie.dto.User.ImageLinkDTO;
import com.mieker.ifpr.shelfie.dto.User.RegisterUserDTO;
import com.mieker.ifpr.shelfie.dto.User.UpdateUserDTO;
import com.mieker.ifpr.shelfie.dto.User.UserDTO;
import com.mieker.ifpr.shelfie.entity.User;
import com.mieker.ifpr.shelfie.entity.enumeration.UserRoles;
import com.mieker.ifpr.shelfie.exception.AccessForbiddenException;
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
import java.util.Objects;
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
    private Validation validation;

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
        UUID userId = validation.userAuthenticator();
        if (!userId.equals(id)) {
            throw new AccessForbiddenException("Você não tem permissão para atualizar esse usuário");
        }
        userToUpdate.setNickname(userUpdateDTO.getNickname());
        userToUpdate.setName(userUpdateDTO.getName());
        userRepository.save(userToUpdate);
        return userMapper.userToUpdateUserDTO(userToUpdate);
    }

//    atualizar o usuário para disable
    public String disableUser() {
        UUID userId = validation.userAuthenticator();

        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new IdNotFoundException("User not found with id: " + userId));

        this.onlyAdmin(userToUpdate);

        userToUpdate.setEnabled(false);
        userRepository.save(userToUpdate);
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
//                referências de metodo
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    public String changeRole(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        user.setRole(UserRoles.ROLE_ADMIN);
        userRepository.save(user);
        return "O usuário " + user.getName() + " agora é um administrador";
    }

    public String disableAndEnableUser(UUID userId) {
        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new IdNotFoundException("User not found with id: " + userId));
        String message = "";

        this.onlyAdmin(userToUpdate);
        System.out.println(userToUpdate.getEnabled());
        if (!userToUpdate.getEnabled()) {
            userToUpdate.setEnabled(true);
            message = "Usuário ativado com sucesso";
        } else {
            userToUpdate.setEnabled(false);
            message = "Usuário desativado com sucesso";
        }
        userRepository.save(userToUpdate);
        return message;
    }

    public ImageLinkDTO uploadImage(ImageLinkDTO linkImage) {
        UUID userId = validation.userAuthenticator();
        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new IdNotFoundException("User not found with id: " + userId));
        userToUpdate.setImage(linkImage.getImage());
        userRepository.save(userToUpdate);
        System.out.println(userToUpdate.getImage());
        return userMapper.userToImageLinkDTO(userToUpdate);
    }

    private void onlyAdmin(User userToUpdate) {
        if (Objects.equals(userToUpdate.getNickname(), "admin")) {
            throw new AccessForbiddenException("Não é possível desativar super administrador");
        }
    }

}
