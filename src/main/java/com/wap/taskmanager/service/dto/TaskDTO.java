package com.wap.taskmanager.service.dto;

import com.wap.taskmanager.util.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate expirationDate;
    private TaskStatus status;

}
