package com.wap.taskmanager.service;

import com.wap.taskmanager.service.dto.request.CreateUpdateUserDto;
import com.wap.taskmanager.service.dto.response.UserDTO;

import java.util.List;


public interface UserService {
    UserDTO findById(Long id);
    List<UserDTO> findAll();
    UserDTO saveOrUpdate(CreateUpdateUserDto userDTO);
    void deleteById(Long id);
}
