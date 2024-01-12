package com.wap.taskmanager.service;

import com.wap.taskmanager.service.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    TaskDTO findById(Long id);
    List<TaskDTO> findAll();
    TaskDTO saveOrUpdate(TaskDTO taskDTO);
    void deleteById(Long id);
    TaskDTO assignTaskToUser(Long taskId, Long userId);
}
