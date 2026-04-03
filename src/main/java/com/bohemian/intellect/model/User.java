package com.bohemian.intellect.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

@Entity
@Data
@Table(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Username can only contain letters, numbers, dot, underscore, and hyphen")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 80, message = "Name must be between 2 and 80 characters")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 72, message = "Password must be between 8 and 72 characters")
    private String password;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 120, message = "Email must be at most 120 characters")
    private String email;

    @Column(nullable = false)
    private boolean isVerified;

    @Column(nullable = false)
    @NotEmpty(message = "At least one role is required")
    private List<@NotBlank(message = "Role cannot be blank") String> roles;

    @NotNull(message = "Quiz ID list is required")
    private List<@NotBlank(message = "Quiz ID cannot be blank") @Size(max = 64, message = "Quiz ID must be at most 64 characters") String> quizID;

    @NotNull(message = "Result ID list is required")
    private List<@NotBlank(message = "Result ID cannot be blank") @Size(max = 64, message = "Result ID must be at most 64 characters") String> resultId;

    public User(String id, String username, String name, String password, String email, boolean isVerified, List<String> roles, List<String> quizID, List<String> resultId) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.isVerified = isVerified;
        this.roles = roles;
        this.quizID = quizID;
        this.resultId = resultId;
    }

    public User() {
    }
}
