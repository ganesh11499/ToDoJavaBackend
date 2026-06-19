package com.ganesh.todo.repository;

import com.ganesh.todo.entity.Todo;
import com.ganesh.todo.enums.TodoStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository
        extends JpaRepository<Todo, Long> {

    Page<Todo> findByUserId(
            Long userId,
            Pageable pageable);

    Page<Todo> findByUserIdAndStatus(
            Long userId,
            TodoStatus status,
            Pageable pageable);

        
}