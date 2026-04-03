//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.bohemian.intellect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record QuizCreationRequest(
    @NotBlank(message = "Quiz title is required")
    @Size(min = 3, max = 120, message = "Quiz title must be between 3 and 120 characters")
    String title,

    @NotBlank(message = "Quiz description is required")
    @Size(min = 10, max = 1000, message = "Quiz description must be between 10 and 1000 characters")
    String description
) {
}
