package com.ganesh.todo.controller;
import com.ganesh.todo.dto.TodoResponse;
import com.ganesh.todo.dto.TodoRequest;
import com.ganesh.todo.entity.PageResponse;
import com.ganesh.todo.entity.Todo;
import com.ganesh.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<?> createTodo(

            @RequestBody
            @Valid
            TodoRequest request,

            @RequestHeader("Authorization")
            String authHeader) {

        Todo todo =
                todoService.saveTodo(
                        request,
                        authHeader);

        return ResponseEntity.ok(todo);
    }

    @GetMapping
    public ResponseEntity<PageResponse<TodoResponse>> getMyTodos(

            @RequestHeader("Authorization")
            String authHeader,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(required = false)
            String status) {

        return ResponseEntity.ok(
                todoService.getMyTodos(
                        authHeader,
                        page,
                        size,
                        status));
    }

}