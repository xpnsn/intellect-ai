package com.bohemian.intellect.service;

import com.bohemian.intellect.dto.GenerateRequest;
import com.bohemian.intellect.model.Question;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class AIService {
    private final Client client;
    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;
    private final PromptBuilder promptBuilder;

    public AIService(Client client, ObjectMapper objectMapper, ResourceLoader resourceLoader, PromptBuilder promptBuilder) {
        this.client = client;
        this.objectMapper = objectMapper;
        this.resourceLoader = resourceLoader;
        this.promptBuilder = promptBuilder;
    }

    public List<Question> generateAnswers(Map<String, String> placeholders) {
        String prompt;
        try {
            Resource resource = this.resourceLoader.getResource("classpath:templates/prompts/generate-questions.txt");
            prompt = Files.readString(resource.getFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            prompt = prompt.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }

        GenerateContentResponse response = client.models.generateContent("gemini-2.5-flash", prompt, null);
        String text = response.text();
        int start = text.indexOf('[');
        int end = text.lastIndexOf(']') + 1;
        if (start < 0 || end <= start) {
            throw new IllegalStateException("AI response did not contain a JSON array");
        }

        String json = text.substring(start, end);
        try {
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Question generateQuestion(GenerateRequest request) {
        String prompt = promptBuilder.buildAdaptivePrompt(request);
        return generateQuestionWithRetry(prompt, 1);
    }

    private Question generateQuestionWithRetry(String prompt, int retriesLeft) {
        try {
            GenerateContentResponse response = client.models.generateContent("gemini-2.5-flash", prompt, null);
            String json = extractJsonObject(response.text());
            JsonNode node = objectMapper.readTree(json);

            validateField(node, "question");
            validateField(node, "options");
            validateField(node, "correctAnswer");
            validateField(node, "concept");
            validateField(node, "difficulty");
            validateField(node, "explanation");

            Question question = new Question();
            question.setTitle(node.get("question").asText());
            question.setOptions(objectMapper.convertValue(node.get("options"), new TypeReference<>() {}));
            question.setCorrectAnswer(node.get("correctAnswer").asText());
            question.setConcept(node.get("concept").asText());
            question.setDifficulty(Math.max(1, Math.min(5, node.get("difficulty").asInt())));
            question.setExplanation(node.get("explanation").asText());
            question.setQuizId("ai-quiz");
            return question;
        } catch (Exception e) {
            if (retriesLeft > 0) {
                return generateQuestionWithRetry(prompt, retriesLeft - 1);
            }
            throw new IllegalStateException("Failed to parse adaptive AI response", e);
        }
    }

    private String extractJsonObject(String text) {
        int start = text.indexOf('{');
        int end = text.lastIndexOf('}') + 1;
        if (start < 0 || end <= start) {
            throw new IllegalArgumentException("AI response did not contain a JSON object");
        }
        return text.substring(start, end);
    }

    private void validateField(JsonNode node, String fieldName) {
        if (!node.has(fieldName) || node.get(fieldName).isNull()) {
            throw new IllegalArgumentException("Missing required field: " + fieldName);
        }
    }
}
