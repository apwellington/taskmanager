package com.wap.taskmanager.service.impl;

import com.wap.taskmanager.entity.Task;
import com.wap.taskmanager.entity.User;
import com.wap.taskmanager.exception.TaskException;
import com.wap.taskmanager.exception.TaskNotFoundException;
import com.wap.taskmanager.exception.UserNotFoundException;
import com.wap.taskmanager.repository.TaskRepository;
import com.wap.taskmanager.repository.UserRepository;
import com.wap.taskmanager.service.TaskService;
import com.wap.taskmanager.service.dto.TaskDTO;
import com.wap.taskmanager.service.mapper.TaskMapper;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;
    private final TaskMapper taskMapper;


    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }


    @Override
    public TaskDTO findById(Long id) {
        try {

            Task task = taskRepository.findById(id)
                    .orElseThrow(() -> new TaskNotFoundException("Task not found by id:" + id));
            return taskMapper.taskToTaskDTO(task);

        } catch (TaskNotFoundException e) {
            String errorMessage = "Task not found by id:" + id;
            log.error(errorMessage, e);
            throw e;

        } catch (Exception e) {
            log.error("Error trying to find task by ID: {}", id, e);
            throw new TaskException("Error trying to find task by ID: " + id);
        }
    }

    @Override
    public List<TaskDTO> findAll() {
        try {
            return taskRepository.findAll().stream().map(taskMapper::taskToTaskDTO).collect(Collectors.toList());
        } catch (Exception e) {
            String errorMessage = "Error getting all tasks";
            log.error(errorMessage, e);
            throw new TaskException(errorMessage);
        }
    }

    @Transactional
    @Override
    public TaskDTO saveOrUpdate(TaskDTO taskDTO) {
        try {
            Task task = taskMapper.taskDTOToTask(taskDTO);
            Task savedTask = taskRepository.save(task);
            return taskMapper.taskToTaskDTO(savedTask);
        } catch (Exception e) {
            String errorMessage = "Error Saving or updating task";
            log.error(errorMessage, e);
            throw new TaskException("errorMessage: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Not found task by id: " + id));
            taskRepository.delete(task);
            log.info("Task Successfully deleted. ID: {}", id);
        } catch (TaskNotFoundException e) {
            String errorMessage = "Not found task by id: " + id;
            log.error(errorMessage, e);
            throw e;
        } catch (Exception e) {
            String errorMessage = "Error trying to delete task : " + id;
            log.error(errorMessage, e);
            throw new TaskException(errorMessage);
        }

    }

    @Transactional
    @Override
    public TaskDTO assignTaskToUser(Long taskId, Long userId) {
        try {
            Task task = this.taskRepository.findById(taskId)
                    .orElseThrow(() -> new TaskNotFoundException("Task not found by id:" + taskId));

            User user = this.userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found by id:" + userId));
            if (user.getTasks().contains(task)) {
                log.warn("The task is already assigned to the user. Task ID: {}, User ID: {}", taskId, userId);
                return taskMapper.taskToTaskDTO(task);
            }
            user.getTasks().add(task);
            this.userRepository.save(user);
            return taskMapper.taskToTaskDTO(task);
        } catch (TaskNotFoundException | UserNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Error assigning task to user. Task ID: {}, User ID: {}", taskId, userId, e);
            throw new TaskException("Error assigning task to user. Task ID: " + taskId + ", User ID: " + userId);
        }
    }
}
