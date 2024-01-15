package com.wap.taskmanager.service.dto.request;

import com.wap.taskmanager.util.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateUserDto {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

    private UserRole role;
}
