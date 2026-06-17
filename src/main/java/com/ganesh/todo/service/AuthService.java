package com.ganesh.todo.service;
import com.ganesh.todo.dto.LoginRequest;
import com.ganesh.todo.dto.LoginResponse;
import com.ganesh.todo.entity.User;
import com.ganesh.todo.repository.UserRepository;
import com.ganesh.todo.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponse login(
            LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid Email or Password"));

        boolean matches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword());

        if (!matches) {
            throw new RuntimeException(
                    "Invalid Email or Password");
        }

        String token =
                jwtUtil.generateToken(
                        user.getEmail());

        return LoginResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .token(token)
                .build();
    }
}