package com.wap.taskmanager.service.impl;

import com.wap.taskmanager.entity.AssignmentTask;
import com.wap.taskmanager.entity.Task;
import com.wap.taskmanager.entity.User;
import com.wap.taskmanager.exception.TaskException;
import com.wap.taskmanager.exception.TaskNotFoundException;
import com.wap.taskmanager.exception.UserNotFoundException;
import com.wap.taskmanager.repository.AssignmentTaskRepository;
import com.wap.taskmanager.repository.TaskRepository;
import com.wap.taskmanager.repository.UserRepository;
import com.wap.taskmanager.service.TaskService;
import com.wap.taskmanager.service.dto.request.CreateUpdateTaskDto;
import com.wap.taskmanager.service.dto.response.TaskDTO;
import com.wap.taskmanager.service.mapper.TaskMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final AssignmentTaskRepository assignmentTaskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;


    public TaskServiceImpl(TaskRepository taskRepository, AssignmentTaskRepository assignmentTaskRepository, UserRepository userRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.assignmentTaskRepository = assignmentTaskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }


    @Override
    public TaskDTO findById(Long id) {
        log.info("TaskServiceImpl | findById({})", id);
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
        log.info("TaskServiceImpl | findAll()");
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
    public TaskDTO saveOrUpdate(CreateUpdateTaskDto taskDTO) {
        log.info("TaskServiceImpl | saveOrUpdate({})", taskDTO);

        try {
            Task task = taskMapper.createUpdateTaskDTOToTask(taskDTO);
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
        log.info("TaskServiceImpl | deleteById({})", id);
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
        log.info("TaskServiceImpl | assignTaskToUser({}, {})", taskId, userId);
        try {
            Task task = this.taskRepository.findById(taskId)
                    .orElseThrow(() -> new TaskNotFoundException("Task not found by id:" + taskId));

            User user = this.userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found by id:" + userId));


            this.assignmentTaskRepository.findByUserAndTask(user, task)
                    .ifPresent(
                            assignmentTask -> {
                                log.warn("The task is already assigned to the user. Task ID: {}, User ID: {}", taskId, userId);
                                throw new TaskException("The task is already assigned to the user. Task ID: " + taskId + ", User ID: " + userId);
                            }
                    );

            AssignmentTask assignmentTask = new AssignmentTask();
            assignmentTask.setTask(task);
            assignmentTask.setUser(user);
            this.assignmentTaskRepository.save(assignmentTask);
            return this.taskMapper.taskToTaskDTO(task);

        } catch (TaskNotFoundException | UserNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Error assigning task to user. Task ID: {}, User ID: {}", taskId, userId, e);
            throw new TaskException("Error assigning task to user. Task ID: " + taskId + ", User ID: " + userId);
        }
    }
}
