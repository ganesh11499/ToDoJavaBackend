package com.ganesh.todo.controller;
import com.ganesh.todo.dto.LoginRequest;
import com.ganesh.todo.dto.LoginResponse;
import com.ganesh.todo.dto.RegisterRequest;
import com.ganesh.todo.entity.ApiResponse;
import com.ganesh.todo.service.AuthService;
import com.ganesh.todo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(

           @Valid @RequestBody RegisterRequest request) {

        String message = userService.register(request);

        ApiResponse response = ApiResponse.builder()
                .status(200)
                .message("User registered successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        Map<String, Object> result = new HashMap<>();

        result.put("status", 200);
        result.put("message", "Login Successful");
        result.put("id", response.getId());
        result.put("fullName", response.getFullName());
        result.put("email", response.getEmail());
        result.put("token", response.getToken());

        return ResponseEntity.ok(result);
    }



}