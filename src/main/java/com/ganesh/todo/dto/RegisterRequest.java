package com.ganesh.todo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String fullName;

    @Email
    private String email;

    @Size(min = 6, max = 20)
    private String password;

    @Pattern(regexp = "^[0-9]{10}$")
    private String mobileNumber;
}
