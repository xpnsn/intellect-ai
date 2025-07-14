package com.bohemian.intellect.controller;

import com.bohemian.intellect.dto.QuizCreationRequest;
import com.bohemian.intellect.model.Quiz;
import com.bohemian.intellect.service.QuizService;
import java.util.List;
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
@RequestMapping({"quiz"})
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<?> saveQuiz(@RequestBody QuizCreationRequest request) {
        return this.quizService.saveNewQuiz(request);
    }

    @DeleteMapping({"{id}"})
    public ResponseEntity<?> deleteQuiz(@PathVariable String id) {
        return this.quizService.deleteQuiz(id);
    }

    @GetMapping({"{id}"})
    public ResponseEntity<?> getQuizById(@PathVariable String id) {
        return this.quizService.getQuizById(id);
    }

    @GetMapping
    public ResponseEntity<?> getQuizByUsername() {
        List<Quiz> quizzes = this.quizService.getQuizByUsername();
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @PostMapping({"update/{id}"})
    public ResponseEntity<?> updateQuiz(@RequestBody QuizCreationRequest req, @PathVariable String id) {
        return this.quizService.updateQuiz(id, req);
    }
}
