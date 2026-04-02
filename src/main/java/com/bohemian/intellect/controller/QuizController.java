package com.bohemian.intellect.controller;

import com.bohemian.intellect.dto.QuizCreationRequest;
import com.bohemian.intellect.model.Question;
import com.bohemian.intellect.model.Quiz;
import com.bohemian.intellect.service.AIService;
import com.bohemian.intellect.service.QuizService;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"quiz"})
public class QuizController {
    private final QuizService quizService;
    private final AIService aiService;

    public QuizController(QuizService quizService, AIService aiService) {
        this.quizService = quizService;
        this.aiService = aiService;
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

//    @PostMapping({"ai"})
//    public ResponseEntity<?> aiQuiz(@RequestParam String topic, @RequestParam String size, @RequestParam String level) {
//        List<Question> questions = this.aiService.generateAnswers(Map.of("topic", topic, "size", size, "level", level));
//        return new ResponseEntity<>(questions, HttpStatus.OK);
//    }
}
