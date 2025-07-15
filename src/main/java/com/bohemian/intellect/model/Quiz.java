package com.bohemian.intellect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "quiz_table")
public class Quiz {
    @Id
    @GeneratedValue(
        strategy = GenerationType.UUID
    )
    private String id;
    private String title;
    private String description;
    private String username;
    private List<Long> questionId;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUsername() {
        return username;
    }

    public List<Long> getQuestionId() {
        return questionId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setQuestionId(List<Long> questionId) {
        this.questionId = questionId;
    }

    public Quiz(String id, String title, String description, String username, List<Long> questionId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.username = username;
        this.questionId = questionId;
    }

    public Quiz() {
    }
}
