package com.bohemian.intellect.controller;

import com.bohemian.intellect.dto.QuizCreationRequest;
import com.bohemian.intellect.model.Quiz;
import com.bohemian.intellect.service.AIService;
import com.bohemian.intellect.service.QuizService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping({"quiz"})
public class QuizController {
    private final QuizService quizService;
    private final AIService aiService;

    public QuizController(QuizService quizService, AIService aiService) {
        this.quizService = quizService;
        this.aiService = aiService;
    }

    @PostMapping
    public ResponseEntity<?> saveQuiz(@Valid @RequestBody QuizCreationRequest request) {
        return this.quizService.saveNewQuiz(request);
    }

    @DeleteMapping({"{id}"})
    public ResponseEntity<?> deleteQuiz(@PathVariable @NotBlank(message = "Quiz ID is required") String id) {
        return this.quizService.deleteQuiz(id);
    }

    @GetMapping({"{id}"})
    public ResponseEntity<?> getQuizById(@PathVariable @NotBlank(message = "Quiz ID is required") String id) {
        return this.quizService.getQuizById(id);
    }

    @GetMapping
    public ResponseEntity<?> getQuizByUsername() {
        List<Quiz> quizzes = this.quizService.getQuizByUsername();
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @PostMapping({"update/{id}"})
    public ResponseEntity<?> updateQuiz(@Valid @RequestBody QuizCreationRequest req, @PathVariable @NotBlank(message = "Quiz ID is required") String id) {
        return this.quizService.updateQuiz(id, req);
    }

//    @PostMapping({"ai"})
//    public ResponseEntity<?> aiQuiz(@RequestParam String topic, @RequestParam String size, @RequestParam String level) {
//        List<Question> questions = this.aiService.generateAnswers(Map.of("topic", topic, "size", size, "level", level));
//        return new ResponseEntity<>(questions, HttpStatus.OK);
//    }
}
