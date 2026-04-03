package com.bohemian.intellect.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Map;

public record EmailSenderDto(
    @NotBlank(message = "Recipient email is required")
    @Email(message = "Recipient email must be valid")
    String to,

    @NotBlank(message = "Email type is required")
    @Size(max = 50, message = "Email type must be at most 50 characters")
    String type,

    @NotBlank(message = "Email subject is required")
    @Size(max = 150, message = "Email subject must be at most 150 characters")
    String subject,

    @NotNull(message = "Template data is required")
    Map<String, String> data
) {}
