package com.ganesh.todo.service;

import com.ganesh.todo.dto.RegisterRequest;
import com.ganesh.todo.entity.User;
import com.ganesh.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String register(RegisterRequest request,
                            MultipartFile image)
            throws IOException{

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email Already Exists");
        }

        String imagePath = saveImage(image);

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .mobileNumber(request.getMobileNumber())
                .profileImage(imagePath)
                .password(
                        passwordEncoder.encode(
                                request.getPassword()))
                .build();
        userRepository.save(user);

        return "User Registered Successfully";
    }

    private String saveImage(MultipartFile image)
            throws IOException {

        String uploadDir = "uploads/";

        String fileName =
                UUID.randomUUID()
                        + "_"
                        + image.getOriginalFilename();

        Path path =
                Paths.get(uploadDir,fileName);

        Files.createDirectories(path.getParent());

        Files.write(path,image.getBytes());

        return fileName;
    }

}
