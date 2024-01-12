package com.wap.taskmanager.service;

import com.wap.taskmanager.service.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService {
    UserDTO findById(Long id);
    List<UserDTO> findAll();
    UserDTO saveOrUpdate(UserDTO userDTO);
    void deleteById(Long id);
}