package com.bohemian.intellect.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Table(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private boolean isVerified;

    @Column(nullable = false)
    private List<String> roles;

    private List<String> quizID;

    private List<String> resultId;

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
