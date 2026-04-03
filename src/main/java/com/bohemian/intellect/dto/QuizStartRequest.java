//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.bohemian.intellect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record QuizStartRequest(
    @NotBlank(message = "Quiz ID is required")
    @Size(max = 64, message = "Quiz ID must be at most 64 characters")
    String quizId
) {
}
