package com.ganesh.todo.controller;

import com.ganesh.todo.dto.TodoRequest;
import com.ganesh.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ToDoController {
    private final TodoService todoService;
    @PostMapping("/todos")
    public ResponseEntity<?> saveTodo(
            @RequestBody TodoRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                todoService.saveTodo(
                        request,
                        email));
    }
}
