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
@AllArgsConstructor
@NoArgsConstructor
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

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public List<String> getRoles() {
        return roles;
    }

    public List<String> getQuizID() {
        return quizID;
    }

    public List<String> getResultId() {
        return resultId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setQuizID(List<String> quizID) {
        this.quizID = quizID;
    }

    public void setResultId(List<String> resultId) {
        this.resultId = resultId;
    }

}
