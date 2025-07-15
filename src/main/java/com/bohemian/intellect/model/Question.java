package com.bohemian.intellect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "question_table")
@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE
    )
    private Long id;
    private String title;
    private List<String> options;
    private String correctAnswer;
    private String quizId;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getQuizId() {
        return quizId;
    }

    public Question(Long id, String title, List<String> options, String correctAnswer, String quizId) {
        this.id = id;
        this.title = title;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.quizId = quizId;
    }

    public Question() {
    }
}
