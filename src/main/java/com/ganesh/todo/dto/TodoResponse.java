package com.ganesh.todo.dto;


import com.ganesh.todo.enums.TodoStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TodoResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private TodoStatus status;
}