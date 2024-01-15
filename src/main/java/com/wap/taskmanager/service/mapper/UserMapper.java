package com.wap.taskmanager.service.mapper;

import com.wap.taskmanager.entity.User;
import com.wap.taskmanager.service.dto.request.CreateUpdateUserDto;
import com.wap.taskmanager.service.dto.response.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);
    User createUpdateUserDTOToUser(CreateUpdateUserDto createUpdateUserDto);

}
