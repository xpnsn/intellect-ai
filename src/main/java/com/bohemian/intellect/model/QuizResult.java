//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.bohemian.intellect.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "result_table")
@Data
public class QuizResult {
    @Id
    @GeneratedValue(
        strategy = GenerationType.UUID
    )
    private String resultID;

    @NotBlank(message = "Quiz ID is required")
    @Size(max = 64, message = "Quiz ID must be at most 64 characters")
    private String quizId;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    private String username;

    @Min(value = 0, message = "Total questions cannot be negative")
    private int totalQuestions;

    @Min(value = 0, message = "Correct answers cannot be negative")
    private int correctAnswers;

    @NotNull(message = "Answers list is required")
    private List<@NotBlank(message = "Answer cannot be blank") String> answers;

    @NotNull(message = "Start time is required")
    private LocalDateTime startedAt;

    @NotNull(message = "End time is required")
    private LocalDateTime endedAt;


    public QuizResult(String resultID, String quizId, String username, int totalQuestions, int correctAnswers, List<String> answers, LocalDateTime startedAt, LocalDateTime endedAt) {
        this.resultID = resultID;
        this.quizId = quizId;
        this.username = username;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.answers = answers;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

    public QuizResult() {
    }
}
