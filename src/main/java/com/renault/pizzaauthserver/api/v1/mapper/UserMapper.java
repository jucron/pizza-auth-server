package com.renault.pizzaauthserver.api.v1.mapper;

import com.renault.pizzaauthserver.api.v1.model.UserDTO;
import com.renault.pizzaauthserver.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);

//    User userDTOToUser(UserDTO userDTO);

}
