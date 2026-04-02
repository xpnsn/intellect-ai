package com.bohemian.intellect.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Intellect AI API",
                version = "v1",
                description = "API documentation for Intellect AI backend"
        )
)
public class OpenApiConfig {}

