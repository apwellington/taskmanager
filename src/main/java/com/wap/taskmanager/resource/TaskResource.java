package com.wap.taskmanager.resource;


import com.wap.taskmanager.service.TaskService;
import com.wap.taskmanager.service.dto.request.CreateUpdateTaskDto;
import com.wap.taskmanager.service.dto.response.TaskDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Log4j2
public class TaskResource {
    private final TaskService taskService;

    public TaskResource(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("")
    public ResponseEntity<List<TaskDTO>> findAll() {
        log.info("TaskResource | findAll()");
        List<TaskDTO> tasks = taskService.findAll();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> findById(@PathVariable Long taskId) {
        log.info("TaskResource | findById({})", taskId);
        TaskDTO taskDTO = taskService.findById(taskId);
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }

    @PutMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<TaskDTO> assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId) {
        log.info("TaskResource | assignTaskToUser({}, {})", taskId, userId);
        TaskDTO assignedTask = taskService.assignTaskToUser(taskId, userId);
        return new ResponseEntity<>(assignedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long taskId) {
        log.info("TaskResource | deleteById({})", taskId);
        taskService.deleteById(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("")
    public ResponseEntity<TaskDTO> saveOrUpdate(@RequestBody CreateUpdateTaskDto taskDTO) {
        log.info("TaskResource | saveOrUpdate({})", taskDTO);
        TaskDTO savedTask = taskService.saveOrUpdate(taskDTO);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

}
