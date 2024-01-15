package com.wap.taskmanager.service.dto.request;


import com.wap.taskmanager.util.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateTaskDto {

    @NotBlank
    private String name;

    private String description;
    private String dueDate;
    
    private TaskStatus status;
}
