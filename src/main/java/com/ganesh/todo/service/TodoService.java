package com.ganesh.todo.service;
import com.ganesh.todo.dto.TodoRequest;
import com.ganesh.todo.entity.Todo;
import com.ganesh.todo.entity.User;
import com.ganesh.todo.enums.TodoStatus;
import com.ganesh.todo.repository.TodoRepository;
import com.ganesh.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public String saveTodo(
            TodoRequest request,
            String email) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User Not Found"));

        Todo todo = Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .status(
                        request.getStatus() == null
                                ? TodoStatus.PENDING
                                : request.getStatus())
                .user(user)
                .build();

        todoRepository.save(todo);

        return "Todo Created Successfully";
    }
}