package com.bohemian.intellect.service;

import com.bohemian.intellect.dto.GenerateRequest;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

    public String buildAdaptivePrompt(GenerateRequest request) {
        return """
            You are an AI tutor.

            Mode: %s

            Context:
            - Topic: %s
            - Previous Question: %s
            - User Answer: %s
            - Was Correct: %s
            - Target Concept: %s
            - Difficulty Shift: %d

            Rules:
            IF incorrect:
            - Generate simpler prerequisite question
            IF correct:
            - Generate deeper application-based question

            Return JSON:
            {
              "question": "...",
              "options": ["A", "B", "C", "D"],
              "correctAnswer": "...",
              "concept": "...",
              "difficulty": 1,
              "explanation": "...",
              "nextAction": "remedial | advance"
            }
            """.formatted(
            safe(request.getMode()),
            safe(request.getTopic()),
            safe(request.getLastQuestion()),
            safe(request.getUserAnswer()),
            request.isWasCorrect(),
            safe(request.getTargetConcept()),
            request.getDifficultyShift()
        );
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}

