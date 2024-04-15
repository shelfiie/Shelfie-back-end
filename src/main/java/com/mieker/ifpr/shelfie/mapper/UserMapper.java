package com.mieker.ifpr.shelfie.mapper;

import com.mieker.ifpr.shelfie.dto.RegisterUserDTO;
import com.mieker.ifpr.shelfie.dto.UpdateUserDTO;
import com.mieker.ifpr.shelfie.dto.UserDTO;
import com.mieker.ifpr.shelfie.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
@Component
public class UserMapper {

    private final ModelMapper mapper;

    @Autowired
    public UserMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public User signUpToUser(RegisterUserDTO registerDTO) throws ParseException {
        return mapper.map(registerDTO, User.class);
    }

    // Método para converter um objeto User em um UpdateUserDTO
    public UpdateUserDTO userToUpdateUserDTO(User user) {
        return this.mapper.map(user, UpdateUserDTO.class);
    }

//    mapeia os valores do DTO updateUserDTO para o objeto user e retorna o objeto user atualizado.
//    public User updateUserFromDTO(User user, UpdateUserDTO updateUserDTO) {
////        tem que passar o updateuserDTO que são os dados atualizados, e o user que vai ser atualizado
//        return mapper.map(user, updateUserDTO);
////        return user;
//    }

    public UserDTO updateUserDisabled(User user) {
//        user.setEnabled(false);
        return mapper.map(user, UserDTO.class);
    }

    public UserDTO userToUserDTO(User user) {
        return this.mapper.map(user, UserDTO.class);
    }
}
