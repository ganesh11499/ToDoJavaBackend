package com.ganesh.todo.entity;
import jakarta.persistence.*;
import com.ganesh.todo.entity.BaseEntity;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String mobileNumber;

    private String profileImage;

    @Column(nullable = false)
    private Boolean active = true;
}