//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.bohemian.intellect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "result_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResult {
    @Id
    @GeneratedValue(
        strategy = GenerationType.UUID
    )
    private String resultID;
    private String quizId;
    private String username;
    private int totalQuestions;
    private int correctAnswers;
    private List<String> answers;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;


    public String getResultID() {
        return resultID;
    }

    public String getQuizId() {
        return quizId;
    }

    public String getUsername() {
        return username;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }
}
