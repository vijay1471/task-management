package com.taskManagement.controller;

import com.taskManagement.dto.TaskDTO;
import com.taskManagement.entity.enums.TaskStatus;
import com.taskManagement.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping()
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        TaskDTO createdTask = taskService.createTask(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable String id) {
        TaskDTO taskDTO = taskService.getTaskById(id);
        return ResponseEntity.status(HttpStatus.OK).body(taskDTO);
    }

    @GetMapping()
    public ResponseEntity<List<TaskDTO>> getAllTasks(@RequestParam(required = false, defaultValue = "0") int page,
                                                     @RequestParam(required = false, defaultValue = "10") int size,
                                                     @RequestParam(required = false) TaskStatus status) {
        List<TaskDTO> tasks = taskService.getAllTasks(page, size, status);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);

    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable String id, @Valid @RequestBody TaskDTO taskDTO) {
        TaskDTO updatedTask = taskService.updateTask(id, taskDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
