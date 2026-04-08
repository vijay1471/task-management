package com.taskManagement.service;

import com.taskManagement.dto.TaskDTO;
import com.taskManagement.entity.enums.TaskStatus;

import java.util.List;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO);
    TaskDTO getTaskById(String id);
    List<TaskDTO> getAllTasks(int page, int size, TaskStatus status);
    TaskDTO updateTask(String id, TaskDTO taskDTO);
    void deleteTask(String id);
}
