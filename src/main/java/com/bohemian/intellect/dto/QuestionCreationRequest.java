//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.bohemian.intellect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

public record QuestionCreationRequest(
    @NotBlank(message = "Question title is required")
    @Size(min = 5, max = 300, message = "Question title must be between 5 and 300 characters")
    String title,

    @NotEmpty(message = "Options are required")
    @Size(min = 2, max = 6, message = "Options must contain between 2 and 6 items")
    List<@NotBlank(message = "Option value cannot be blank") @Size(max = 200, message = "Option must be at most 200 characters") String> options,

    @NotBlank(message = "Correct answer is required")
    @Size(max = 200, message = "Correct answer must be at most 200 characters")
    String correctAnswer,

    @NotBlank(message = "Quiz ID is required")
    @Size(max = 64, message = "Quiz ID must be at most 64 characters")
    String quizId
) {}
