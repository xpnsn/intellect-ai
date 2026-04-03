package com.bohemian.intellect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

@Entity(name = "question_table")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Question title is required")
    @Size(min = 5, max = 300, message = "Question title must be between 5 and 300 characters")
    private String title;

    @NotEmpty(message = "Options are required")
    @Size(min = 2, max = 6, message = "Options must contain between 2 and 6 items")
    private List<@NotBlank(message = "Option cannot be blank") @Size(max = 200, message = "Option must be at most 200 characters") String> options;

    @NotBlank(message = "Correct answer is required")
    @Size(max = 200, message = "Correct answer must be at most 200 characters")
    private String correctAnswer;

    @NotBlank(message = "Quiz ID is required")
    @Size(max = 64, message = "Quiz ID must be at most 64 characters")
    private String quizId;

    @Size(max = 120, message = "Concept must be at most 120 characters")
    private String concept;

    @Size(max = 1000, message = "Explanation must be at most 1000 characters")
    private String explanation;

    @Min(value = 1, message = "Difficulty must be between 1 and 5")
    @Max(value = 5, message = "Difficulty must be between 1 and 5")
    private int difficulty;

    public Question(Long id, String title, List<String> options, String correctAnswer, String quizId) {
        this(id, title, options, correctAnswer, quizId, null, null, 1);
    }

    public Question(Long id, String title, List<String> options, String correctAnswer, String quizId,
                    String concept, String explanation, int difficulty) {
        this.id = id;
        this.title = title;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.quizId = quizId;
        this.concept = concept;
        this.explanation = explanation;
        this.difficulty = difficulty;
    }

    public Question() {
    }

}
