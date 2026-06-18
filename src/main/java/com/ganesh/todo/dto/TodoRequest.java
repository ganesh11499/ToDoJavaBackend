package com.ganesh.todo.dto;


import com.ganesh.todo.enums.TodoStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TodoRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private LocalDate dueDate;

    private TodoStatus status;

}
