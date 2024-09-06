package com.mieker.ifpr.shelfie.mapper;

import com.mieker.ifpr.shelfie.dto.User.ImageLinkDTO;
import com.mieker.ifpr.shelfie.dto.User.RegisterUserDTO;
import com.mieker.ifpr.shelfie.dto.User.UpdateUserDTO;
import com.mieker.ifpr.shelfie.dto.User.UserDTO;
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

    // Metodo para converter um objeto User em um UpdateUserDTO
    public UpdateUserDTO userToUpdateUserDTO(User user) {
        return this.mapper.map(user, UpdateUserDTO.class);
    }

    public UserDTO updateUserDisabled(User user) {
//        user.setEnabled(false);
        return mapper.map(user, UserDTO.class);
    }

    public UserDTO userToUserDTO(User user) {
        return this.mapper.map(user, UserDTO.class);
    }

    public ImageLinkDTO userToImageLinkDTO(User userToUpdate) {
        return mapper.map(userToUpdate, ImageLinkDTO.class);
    }
}
