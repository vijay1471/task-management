package com.taskManagement.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void handleGlobalExceptionTest() {

        Exception exception = new Exception("Unknown error");

        ResponseEntity<String> response = globalExceptionHandler.handleGlobalException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Something went wrong. ", response.getBody());
    }

    @Test
    public void handleTaskNotFoundExceptionTest() {

        String errorMessage = "Task not found with id: 101";
        TaskNotFoundException exception = new TaskNotFoundException(errorMessage);

        ResponseEntity<String> response = globalExceptionHandler.handleTaskNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }

    @Test
    public void handleFormatErrorTest() {

        ResponseEntity<String> response = globalExceptionHandler.handleFormatError();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid date format. Use yyyy-MM-dd", response.getBody());
    }

}
