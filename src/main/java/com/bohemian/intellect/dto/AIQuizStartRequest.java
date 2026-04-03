package com.bohemian.intellect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AIQuizStartRequest(
    @NotBlank(message = "Topic is required")
    @Size(min = 2, max = 80, message = "Topic must be between 2 and 80 characters")
    String topic,

    @NotBlank(message = "Level is required")
    @Pattern(regexp = "^(?i)(beginner|intermediate|advanced)$", message = "Level must be beginner, intermediate, or advanced")
    String level
) {
}
