package com.taskManagement.service;


import com.taskManagement.dto.TaskDTO;
import com.taskManagement.entity.Task;
import com.taskManagement.entity.enums.TaskStatus;
import com.taskManagement.exception.TaskNotFoundException;
import com.taskManagement.repository.TaskRepository;
import com.taskManagement.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;

    private TaskDTO taskDTO;

    @BeforeEach
    public void setUp() {
        task = new Task();
        task.setId(1L);
        task.setTitle("Test");
        task.setDescription("Test Description");
        task.setStatus(TaskStatus.PENDING);
        task.setDueDate(LocalDate.now());

        taskDTO = new TaskDTO("1", "Test", "Test Description", TaskStatus.PENDING, LocalDate.now());
    }

    @Test
    public void createTaskTest() {

        when(taskRepository.save(any(Task.class))).thenReturn(task);
        TaskDTO result = taskService.createTask(taskDTO);

        assertNotNull(result);
        assertEquals("Test", result.title());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void createTaskWithNullStatusTest() {

        TaskDTO dto = new TaskDTO(null, "Test", "Test Description", null, LocalDate.now());
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDTO result = taskService.createTask(dto);
        assertEquals(TaskStatus.PENDING, result.status());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void getTaskByIdTest() {

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        TaskDTO result = taskService.getTaskById("1");

        assertEquals("1", result.id());
        assertEquals("Test", result.title());
        verify(taskRepository, times(1)).findById(any());
    }

    @Test
    public void getTaskByIdNotFoundTest() {

        when(taskRepository.findById(27L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById("27"));
        verify(taskRepository, times(1)).findById(any());
    }

    @Test
    public void getAllTasksWithStatusTest() {

        Page<Task> page = new PageImpl<>(List.of(task));
        when(taskRepository.findByStatus(eq(TaskStatus.PENDING), any(Pageable.class))).thenReturn(page);

        List<TaskDTO> result = taskService.getAllTasks(0, 10, TaskStatus.PENDING);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(taskRepository).findByStatus(eq(TaskStatus.PENDING), any(Pageable.class));
    }

    @Test
    public void getAllTasksAsEmptyTest() {

        when(taskRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        assertThrows(TaskNotFoundException.class, () -> taskService.getAllTasks(0, 10, null));
    }

    @Test
    public void updateTaskTest() {

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDTO result = taskService.updateTask("1", taskDTO);

        assertNotNull(result);
        verify(taskRepository, times(1)).findById(any());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void deleteTaskTest() {

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        taskService.deleteTask("1");

        verify(taskRepository, times(1)).findById(any());
        verify(taskRepository, times(1)).delete(task);
    }

}
