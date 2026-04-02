package com.bohemian.intellect.service;

import com.bohemian.intellect.model.Question;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class AIService {
    private final Client client;
    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    public AIService(Client client, ObjectMapper objectMapper, ResourceLoader resourceLoader) {
        this.client = client;
        this.objectMapper = objectMapper;
        this.resourceLoader = resourceLoader;
    }


    public List<Question> generateAnswers(Map<String, String> placeholders) {

        String prompt = null;
        try {
            Resource resource = this.resourceLoader.getResource("classpath:templates/prompts/generate-questions.txt");
            prompt = Files.readString(resource.getFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(Map.Entry<String, String> entry : placeholders.entrySet()) {
            prompt = prompt.replace("{{"+entry.getKey()+"}}", entry.getValue());
        }
        GenerateContentResponse response = client.models.generateContent("gemini-2.5-flash", prompt, null);

        String res = response.text();
        int start = res.indexOf('[');
        int end = res.lastIndexOf(']') + 1;
        res = res.substring(start, end);
        try {
            return objectMapper.readValue(res, new TypeReference<List<Question>>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
