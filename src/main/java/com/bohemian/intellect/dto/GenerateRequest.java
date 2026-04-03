package com.bohemian.intellect.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateRequest {
    @NotBlank(message = "Topic is required")
    @Size(min = 2, max = 80, message = "Topic must be between 2 and 80 characters")
    private String topic;

    @NotBlank(message = "Mode is required")
    @Size(max = 40, message = "Mode must be at most 40 characters")
    private String mode;

    @Size(max = 300, message = "Last question must be at most 300 characters")
    private String lastQuestion;

    @Size(max = 500, message = "User answer must be at most 500 characters")
    private String userAnswer;

    private boolean wasCorrect;

    @NotBlank(message = "Target concept is required")
    @Size(max = 120, message = "Target concept must be at most 120 characters")
    private String targetConcept;

    @Min(value = -2, message = "Difficulty shift must be at least -2")
    @Max(value = 2, message = "Difficulty shift must be at most 2")
    private int difficultyShift;
}
