package com.bohemian.intellect.service;

import com.bohemian.intellect.dto.QuizCreationRequest;
import com.bohemian.intellect.dto.QuizDto;
import com.bohemian.intellect.exception.ResourceNotFoundException;
import com.bohemian.intellect.model.Quiz;
import com.bohemian.intellect.repository.QuizRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final ObjectMapper mapper;
    private final UserService userService;

    public QuizService(QuizRepository quizRepository, ObjectMapper mapper, UserService userService) {
        this.quizRepository = quizRepository;
        this.mapper = mapper;
        this.userService = userService;
    }

    public ResponseEntity<?> getQuizById(String id) {
        Quiz quiz = (Quiz)this.quizRepository.findById(id).orElseThrow(() -> {
            return new ResourceNotFoundException(id, "Quiz");
        });
        return new ResponseEntity((QuizDto)this.mapper.convertValue(quiz, QuizDto.class), HttpStatus.OK);
    }

    public ResponseEntity<?> saveNewQuiz(QuizCreationRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Quiz quiz = new Quiz((String)null, request.title(), request.description(), username, Collections.emptyList());
        quiz = (Quiz)this.quizRepository.save(quiz);
        this.userService.addQuizToUser(quiz.getId());
        return new ResponseEntity((QuizDto)this.mapper.convertValue(quiz, QuizDto.class), HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteQuiz(String id) {
        this.quizRepository.deleteById(id);
        this.userService.removeQuizToUser(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    public List<Quiz> getQuizByUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.quizRepository.findAllByUsername(username);
    }

    public ResponseEntity<?> updateQuiz(String id, QuizCreationRequest req) {
        Quiz quiz = (Quiz)this.quizRepository.findById(id).orElseThrow(() -> {
            return new ResourceNotFoundException(id, "Quiz");
        });
        quiz.setTitle(req.title());
        quiz.setDescription(req.description());
        quiz = (Quiz)this.quizRepository.save(quiz);
        return new ResponseEntity((QuizDto)this.mapper.convertValue(quiz, QuizDto.class), HttpStatus.OK);
    }

    public void addQuestionToQuiz(String quizId, Long questionId) {
        Quiz quiz = (Quiz)this.quizRepository.findById(quizId).orElseThrow(() -> {
            return new ResourceNotFoundException(quizId, "Quiz");
        });
        quiz.getQuestionId().add(questionId);
        this.quizRepository.save(quiz);
    }

    public void removeQuestionFromQuiz(String quizId, Long questionId) {
        Quiz quiz = (Quiz)this.quizRepository.findById(quizId).orElseThrow(() -> {
            return new ResourceNotFoundException(quizId, "Quiz");
        });
        quiz.getQuestionId().remove(questionId);
        this.quizRepository.save(quiz);
    }
}
