package com.mieker.ifpr.shelfie.mapper;

import com.mieker.ifpr.shelfie.dto.SignUpDTO;
import com.mieker.ifpr.shelfie.dto.UpdateUserDTO;
import com.mieker.ifpr.shelfie.entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.text.ParseException;
@Component
public class UserMapper {

    private final ModelMapper mapper;

    @Autowired
    public UserMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public User signUpToUser(SignUpDTO signUpDTO) throws ParseException {
        return mapper.map(signUpDTO, User.class);
    }

    // Método para converter um objeto User em um UpdateUserDTO
    public UpdateUserDTO userToUpdateUserDTO(User user) {
        return this.mapper.map(user, UpdateUserDTO.class);
    }

//    mapeia os valores do DTO updateUserDTO para o objeto user e retorna o objeto user atualizado.
    public User updateUserFromDTO(User user, UpdateUserDTO updateUserDTO) {
//        tem que passar o updateuserDTO que são os dados atualizados, e o user que vai ser atualizado
        this.mapper.map(updateUserDTO, user);
        return user;
    }
}
