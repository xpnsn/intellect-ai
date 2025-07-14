package com.bohemian.intellect.service;

import com.bohemian.intellect.dto.QuestionCreationRequest;
import com.bohemian.intellect.dto.QuestionDto;
import com.bohemian.intellect.exception.ResourceNotFoundException;
import com.bohemian.intellect.exception.UnauthorizedAccessException;
import com.bohemian.intellect.model.Question;
import com.bohemian.intellect.model.Quiz;
import com.bohemian.intellect.repository.QuestionRepository;
import com.bohemian.intellect.repository.QuizRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final QuizService quizService;
    private final ObjectMapper mapper;

    public QuestionService(QuestionRepository questionRepository, QuizRepository quizRepository, QuizService quizService, ObjectMapper mapper) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
        this.quizService = quizService;
        this.mapper = mapper;
    }

    public ResponseEntity<?> saveNewQuestion(QuestionCreationRequest req) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Quiz quiz = quizRepository.findById(req.quizId()).orElseThrow(() -> {
            return new ResourceNotFoundException(req.quizId(), "Quiz");
        });
        if (quiz.getUsername().equals(username)) {
            Question question = new Question(null, req.title(), req.options(), req.correctAnswer(), req.quizId());
            question = questionRepository.save(question);
            quizService.addQuestionToQuiz(req.quizId(), question.getId());
            return new ResponseEntity<>(mapper.convertValue(question, QuestionDto.class), HttpStatus.CREATED);
        } else {
            throw new UnauthorizedAccessException("User does not have access to this quiz.");
        }
    }

    public QuestionDto getQuestionById(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> {
            return new ResourceNotFoundException(id.toString(), "Question");
        });
        return mapper.convertValue(question, QuestionDto.class);
    }

    public List<Question> getQuestionFromQuiz(String quizId) {
        return questionRepository.findAllByQuizId(quizId);
    }

    public ResponseEntity<?> deleteQuestionById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Question question = questionRepository.findById(id).orElseThrow(() -> {
            return new ResourceNotFoundException(id.toString(), "Question");
        });
        Quiz quiz = quizRepository.findById(question.getQuizId()).orElse(null);

        assert quiz != null;

        if (quiz.getUsername().equals(username)) {
            questionRepository.delete(question);
            quizService.removeQuestionFromQuiz(question.getQuizId(), question.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new UnauthorizedAccessException("User does not have access to this quiz.");
        }
    }
}
