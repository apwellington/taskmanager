package com.wap.taskmanager.service;

import com.wap.taskmanager.entity.User;
import com.wap.taskmanager.service.dto.request.LoginRequestDTO;

public interface AuthenticationService {
    User authenticate(LoginRequestDTO loginRequestDTO);
}
