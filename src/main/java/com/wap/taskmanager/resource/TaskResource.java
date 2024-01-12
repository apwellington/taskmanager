package com.wap.taskmanager.resource;


import com.wap.taskmanager.service.TaskService;
import com.wap.taskmanager.service.dto.TaskDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskResource {
    private final TaskService taskService;

    public TaskResource(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("")
    public ResponseEntity<List<TaskDTO>> findAll() {
        List<TaskDTO> tasks = taskService.findAll();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> findById(@PathVariable Long taskId) {
        TaskDTO taskDTO = taskService.findById(taskId);
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }

    @PutMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<TaskDTO> assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId) {
        TaskDTO assignedTask = taskService.assignTaskToUser(taskId, userId);
        return new ResponseEntity<>(assignedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long taskId) {
        taskService.deleteById(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("")
    public ResponseEntity<TaskDTO> saveOrUpdate(@RequestBody TaskDTO taskDTO) {
        TaskDTO savedTask = taskService.saveOrUpdate(taskDTO);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

}
