package com.wap.taskmanager.resource;


import com.wap.taskmanager.entity.User;
import com.wap.taskmanager.service.AuthenticationService;
import com.wap.taskmanager.service.impl.AuthenticationServiceImpl;
import com.wap.taskmanager.service.JwtService;
import com.wap.taskmanager.service.dto.request.LoginRequestDTO;
import com.wap.taskmanager.service.dto.response.LoginResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationServiceImpl authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginRequestDTO loginRequestDTO) {
        User authenticatedUser = authenticationService.authenticate(loginRequestDTO);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponseDTO loginResponse = new LoginResponseDTO(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }
}