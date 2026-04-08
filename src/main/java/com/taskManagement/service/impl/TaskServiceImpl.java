package com.taskManagement.service.impl;

import com.taskManagement.dto.TaskDTO;
import com.taskManagement.entity.Task;
import com.taskManagement.entity.enums.TaskStatus;
import com.taskManagement.exception.TaskNotFoundException;
import com.taskManagement.repository.TaskRepository;
import com.taskManagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {

        Task task = new Task();
        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());

        if (taskDTO.status() == null){
            task.setStatus(TaskStatus.PENDING);
        }
        else {
            task.setStatus(taskDTO.status());
        }

        task.setDueDate(taskDTO.dueDate());

        Task savedTask = taskRepository.save(task);
        return mapTaskToDTO(savedTask);
    }

    @Override
    public TaskDTO getTaskById(String id) {

        Task task = taskRepository.findById(Long.valueOf(id)).orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        return mapTaskToDTO(task);
    }

    @Override
    public List<TaskDTO> getAllTasks(int page, int size, TaskStatus status) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("dueDate").ascending());

        Page<Task> tasks;
        if (status != null) {
            tasks = taskRepository.findByStatus(status, pageable);     //filter by status if given
        } else {
            tasks = taskRepository.findAll(pageable);
        }

        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("No tasks found");
        }

        //mapping task to taskDTO
        return tasks.getContent().stream().map(this::mapTaskToDTO).toList();
    }

    @Override
    public TaskDTO updateTask(String id, TaskDTO taskDTO) {

        Task task = taskRepository.findById(Long.valueOf(id)).orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        task.setTitle(taskDTO.title());
        task.setDueDate(taskDTO.dueDate());
        task.setDescription(taskDTO.description());
        task.setStatus(taskDTO.status());

        Task updatedTask = taskRepository.save(task);
        return mapTaskToDTO(updatedTask);
    }

    @Override
    public void deleteTask(String id) {
        Task task = taskRepository.findById(Long.valueOf(id)).orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
        taskRepository.delete(task);
    }


    private TaskDTO mapTaskToDTO(Task task) {
        return new TaskDTO(
                String.valueOf(task.getId()),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getDueDate()
        );
    }

}
