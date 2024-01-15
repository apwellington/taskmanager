package com.wap.taskmanager.service.impl;

import com.wap.taskmanager.entity.User;
import com.wap.taskmanager.exception.UserException;
import com.wap.taskmanager.exception.UserNotFoundException;
import com.wap.taskmanager.repository.UserRepository;
import com.wap.taskmanager.service.UserService;
import com.wap.taskmanager.service.dto.request.CreateUpdateUserDto;
import com.wap.taskmanager.service.dto.response.UserDTO;
import com.wap.taskmanager.service.mapper.UserMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDTO findById(@NotNull Long id) {
        log.info("UserServiceImpl | findById({})", id);
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found by id:" + id));
            return userMapper.userToUserDTO(user);
        } catch (UserNotFoundException e) {
            String errorMessage = "User not found by id:" + id;
            log.error(errorMessage, e);
            throw e;
        } catch (Exception e) {
            log.error("Error trying to find user by ID: {}", id, e);
            throw new UserException("Error trying to find user by ID: " + id);
        }
    }

    @Override
    public List<UserDTO> findAll() {
        log.info("UserServiceImpl | findAll()");
        try {
            return userRepository.findAll().stream().map(userMapper::userToUserDTO).collect(Collectors.toList());
        } catch (Exception e) {
            String errorMessage = "Error getting all users";
            log.error(errorMessage, e);
            throw new UserException(errorMessage);
        }
    }

    @Transactional
    @Override
    public UserDTO saveOrUpdate(@NotNull CreateUpdateUserDto userDTO) {
        log.info("UserServiceImpl | saveOrUpdate({})", userDTO);
        try {
             User user = userMapper.createUpdateUserDTOToUser(userDTO);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            return userMapper.userToUserDTO(savedUser);
        } catch (Exception e) {
            String errorMessage = "Error Saving or updating user";
            log.error(errorMessage, e);
            throw new UserException("errorMessage: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(@NotNull Long id) {
        log.info("UserServiceImpl | deleteById({})", id);
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Not found user by id: " + id));
            userRepository.delete(user);
            log.info("User Successfully deleted. ID: {}", id);
        } catch (UserNotFoundException e) {
            String errorMessage = "Not found user by id: " + id;
            log.error(errorMessage, e);
            throw e;
        } catch (Exception e) {
            String errorMessage = "Error trying to delete user : " + id;
            log.error(errorMessage, e);
            throw new UserException(errorMessage);
        }
    }
}
