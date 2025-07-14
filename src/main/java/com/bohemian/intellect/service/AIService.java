package com.bohemian.intellect.service;

import com.bohemian.intellect.model.Question;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class AIService {
    private final OllamaChatModel ollamaChatModel;
    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    public AIService(OllamaChatModel ollamaChatModel, ObjectMapper objectMapper, ResourceLoader resourceLoader) {
        this.ollamaChatModel = ollamaChatModel;
        this.objectMapper = objectMapper;
        this.resourceLoader = resourceLoader;
    }

    public List<Question> generateAnswers(Map<String, String> placeholders) {

        String prompt = null;
        try {
            Resource resource = this.resourceLoader.getResource("classpath:template/prompt/generate-questions.txt");
            prompt = Files.readString(resource.getFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(Map.Entry<String, String> entry : placeholders.entrySet()) {
            prompt = prompt.replace("{{"+entry.getKey()+"}}", entry.getValue());
        }

        String call = this.ollamaChatModel.call(prompt);
        int start = call.indexOf('{');
        int end = call.lastIndexOf('}') + 1;
        call = call.substring(start, end);
        try {
            return objectMapper.readValue(call, new TypeReference<List<Question>>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
