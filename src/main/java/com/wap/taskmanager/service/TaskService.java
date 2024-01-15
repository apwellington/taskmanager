package com.wap.taskmanager.service;

import com.wap.taskmanager.service.dto.request.CreateUpdateTaskDto;
import com.wap.taskmanager.service.dto.response.TaskDTO;

import java.util.List;

public interface TaskService {
    TaskDTO findById(Long id);
    List<TaskDTO> findAll();
    TaskDTO saveOrUpdate(CreateUpdateTaskDto taskDTO);
    void deleteById(Long id);
    TaskDTO assignTaskToUser(Long taskId, Long userId);
}
