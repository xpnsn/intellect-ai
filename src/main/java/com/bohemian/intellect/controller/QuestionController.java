package com.bohemian.intellect.controller;

import com.bohemian.intellect.dto.QuestionCreationRequest;
import com.bohemian.intellect.dto.QuestionDto;
import com.bohemian.intellect.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/question"})
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping({"{id}"})
    public ResponseEntity<?> getQuestionById(@PathVariable Long id) {
        QuestionDto question = questionService.getQuestionById(id);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @GetMapping({"quiz/{id}"})
    public ResponseEntity<?> getQuizById(@PathVariable String id) {
        return new ResponseEntity<>(questionService.getQuestionFromQuiz(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveQuestion(@RequestBody QuestionCreationRequest req) {
        return questionService.saveNewQuestion(req);
    }

    @DeleteMapping({"{id}"})
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        return questionService.deleteQuestionById(id);
    }
}
