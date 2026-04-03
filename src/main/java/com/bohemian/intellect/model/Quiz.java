package com.bohemian.intellect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "quiz_table")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Quiz title is required")
    @Size(min = 3, max = 120, message = "Quiz title must be between 3 and 120 characters")
    private String title;

    @NotBlank(message = "Quiz description is required")
    @Size(min = 10, max = 1000, message = "Quiz description must be between 10 and 1000 characters")
    private String description;

    @NotBlank(message = "Quiz owner username is required")
    @Size(min = 3, max = 30, message = "Quiz owner username must be between 3 and 30 characters")
    private String username;

    @NotNull(message = "Question list is required")
    private List<@NotNull(message = "Question ID cannot be null") Long> questionId;

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
