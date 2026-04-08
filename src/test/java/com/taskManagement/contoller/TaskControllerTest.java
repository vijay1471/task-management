package com.taskManagement.contoller;

import com.taskManagement.controller.TaskController;
import com.taskManagement.dto.TaskDTO;
import com.taskManagement.entity.enums.TaskStatus;
import com.taskManagement.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private TaskDTO taskDTO;

    @BeforeEach
    public void setUp() {
        taskDTO = new TaskDTO("1", "test", "test description", TaskStatus.PENDING, LocalDate.now());
    }

    @Test
    public void createTaskTest() {

        when(taskService.createTask(any(TaskDTO.class))).thenReturn(taskDTO);
        ResponseEntity<TaskDTO> response = taskController.createTask(taskDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(taskService, times(1)).createTask(taskDTO);
    }

    @Test
    public void getTaskByIdTest() {

        when(taskService.getTaskById("2")).thenReturn(taskDTO);
        ResponseEntity<TaskDTO> response = taskController.getTaskById("2");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(taskService, times(1)).getTaskById(any());
    }

    @Test
    public void getAllTasks_Success() {

        List<TaskDTO> tasks = List.of(taskDTO);
        when(taskService.getAllTasks(0, 5, TaskStatus.PENDING)).thenReturn(tasks);
        ResponseEntity<List<TaskDTO>> response = taskController.getAllTasks(0, 5, TaskStatus.PENDING);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void updateTask_Success() {

        when(taskService.updateTask(eq("1"), any(TaskDTO.class))).thenReturn(taskDTO);
        ResponseEntity<TaskDTO> response = taskController.updateTask("1", taskDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(taskService).updateTask("1", taskDTO);
    }

    @Test
    public void deleteTask_Success() {

        doNothing().when(taskService).deleteTask("101");
        ResponseEntity<Void> response = taskController.deleteTask("101");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService, times(1)).deleteTask("101");
    }
}
