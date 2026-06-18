package com.ganesh.todo.service;
import com.ganesh.todo.dto.TodoResponse;
import com.ganesh.todo.dto.TodoRequest;
import com.ganesh.todo.entity.PageResponse;
import com.ganesh.todo.entity.Todo;
import com.ganesh.todo.entity.User;
import com.ganesh.todo.enums.TodoStatus;
import com.ganesh.todo.repository.TodoRepository;
import com.ganesh.todo.repository.UserRepository;
import com.ganesh.todo.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public Todo saveTodo(
            TodoRequest request,
            String authHeader) {

        String token =
                authHeader.replace("Bearer ", "");

        String email =
                jwtUtil.extractUsername(token);

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Todo todo = new Todo();

        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setDueDate(request.getDueDate());
        todo.setStatus(request.getStatus());
        todo.setUser(user);

        return todoRepository.save(todo);
    }

    //List Bussiness Login
    public PageResponse<TodoResponse> getMyTodos(
            String authHeader,
            int page,
            int size,
            String status) {

        String token =
                authHeader.replace("Bearer ", "");

        String email =
                jwtUtil.extractUsername(token);

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by("id").descending());

        Page<Todo> todoPage;

        if (status != null &&
                !status.isBlank()) {

            todoPage =
                    todoRepository
                            .findByUserIdAndStatus(
                                    user.getId(),
                                    TodoStatus.valueOf(
                                            status.toUpperCase()),
                                    pageable);
        } else {

            todoPage =
                    todoRepository
                            .findByUserId(
                                    user.getId(),
                                    pageable);
        }

        List<TodoResponse> todos =
                todoPage.getContent()
                        .stream()
                        .map(todo ->
                                TodoResponse.builder()
                                        .id(todo.getId())
                                        .title(todo.getTitle())
                                        .description(todo.getDescription())
                                        .dueDate(todo.getDueDate())
                                        .status(todo.getStatus())
                                        .build())
                        .toList();

        return PageResponse.<TodoResponse>builder()
                .content(todos)
                .currentPage(todoPage.getNumber())
                .totalPages(todoPage.getTotalPages())
                .totalElements(todoPage.getTotalElements())
                .last(todoPage.isLast())
                .build();
    }
}