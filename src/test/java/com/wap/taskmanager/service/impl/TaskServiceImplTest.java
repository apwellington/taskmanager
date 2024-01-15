package com.wap.taskmanager.service.impl;

import com.wap.taskmanager.entity.AssignmentTask;
import com.wap.taskmanager.entity.Task;
import com.wap.taskmanager.entity.User;
import com.wap.taskmanager.repository.AssignmentTaskRepository;
import com.wap.taskmanager.repository.TaskRepository;
import com.wap.taskmanager.repository.UserRepository;
import com.wap.taskmanager.service.mapper.TaskMapper;
import com.wap.taskmanager.util.TaskStatus;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AssignmentTaskRepository assignmentTaskRepository;

    @Mock
    private TaskMapper taskMapper;
    @InjectMocks
    private TaskServiceImpl taskService;


    @Test
    public void testAssignTaskToUser() {

        Task task = getMockTask(1L);
        User user = getMockUser(1L);

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(assignmentTaskRepository.findByUserAndTask(user, task)).thenReturn(Optional.empty());

        taskService.assignTaskToUser(task.getId(), user.getId());

        verify(taskRepository, times(1)).findById(task.getId());
        verify(userRepository, times(1)).findById(user.getId());
        verify(assignmentTaskRepository, times(1)).findByUserAndTask(user, task);
        verify(assignmentTaskRepository, times(1)).save(any(AssignmentTask.class));

    }

    @Test
    public void testAssignTaskToUserWhenTaskNotFound() {

        Task task = getMockTask(1L);
        User user = getMockUser(1L);

        when(taskRepository.findById(task.getId())).thenReturn(Optional.empty());
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(assignmentTaskRepository.findByUserAndTask(user, task)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> taskService.assignTaskToUser(task.getId(), user.getId()));

        verify(taskRepository, times(1)).findById(task.getId());
        verify(userRepository, times(0)).findById(user.getId());
        verify(assignmentTaskRepository, times(0)).findByUserAndTask(user, task);
        verify(assignmentTaskRepository, times(0)).save(any(AssignmentTask.class));

    }

    private static User getMockUser(Long userId) {
        User user = new User();

        user.setId(userId);
        user.setUsername("test");
        user.setPassword("test");
        return user;
    }

    private static Task getMockTask(Long taskId) {
        Task task = new Task();
        task.setDescription("Just For Test");
        task.setId(taskId);
        task.setName("Unit Testing Task");
        task.setStatus(TaskStatus.PENDING);
        task.setExpirationDate(LocalDate.now());
        return task;
    }
}