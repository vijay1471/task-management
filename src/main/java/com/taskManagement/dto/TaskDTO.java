package com.taskManagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.taskManagement.entity.enums.TaskStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TaskDTO (String id,

                        @NotBlank(message = "Title cannot be blank")
                        @NotNull(message = "Title cannot be null")
                        String title,

                        String description,

                        TaskStatus status,

                        @NotNull(message = "Due date is required")
                        @Future(message = "Due date must be in the future")
                        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
                        LocalDate dueDate){

}
