//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.bohemian.intellect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record QuestionResponse(
    @NotBlank(message = "Answer is required")
    @Size(max = 500, message = "Answer must be at most 500 characters")
    String answer,
    String explanation,
    String concept,
    Boolean previousCorrect
) {
    public QuestionResponse(String answer) {
        this(answer, null, null, null);
    }
}
