package com.bohemian.intellect.controller;

import com.bohemian.intellect.dto.AIQuizStartRequest;
import com.bohemian.intellect.dto.GenerateRequest;
import com.bohemian.intellect.dto.QuestionDto;
import com.bohemian.intellect.dto.QuestionResponse;
import com.bohemian.intellect.dto.QuizStartRequest;
import com.bohemian.intellect.model.Question;
import com.bohemian.intellect.model.QuizSession;
import com.bohemian.intellect.service.AIService;
import com.bohemian.intellect.service.QuizSocketService;
import com.bohemian.intellect.service.SessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import java.security.Principal;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Controller
@Validated
public class QuizSocketController {
    private final SessionManager sessionManager;
    private final ObjectMapper mapper;
    private final AIService aiService;
    private final QuizSocketService quizSocketService;

    public QuizSocketController(SessionManager sessionManager, ObjectMapper mapper, AIService aiService, QuizSocketService quizSocketService) {
        this.sessionManager = sessionManager;
        this.mapper = mapper;
        this.aiService = aiService;
        this.quizSocketService = quizSocketService;
    }

    @MessageMapping({"/quiz/start"})
    @SendToUser({"/queue/questions"})
    public QuestionDto handleQuizStart(@Valid QuizStartRequest req, Principal principal) {
        return quizSocketService.startQuiz(req, principal);
    }

    @MessageMapping({"/ai/quiz/start"})
    @SendToUser({"/queue/questions"})
    public QuestionDto handleAIQuizStart(@Valid AIQuizStartRequest req, Principal principal) {
        String username = principal.getName();
        QuizSession session = sessionManager.startAiSession(username, req.topic(), req.level());

        GenerateRequest request = new GenerateRequest(req.topic(), "diagnostic", "", "", false, req.topic(), 0);
        Question question = aiService.generateQuestion(request);

        session.setLastQuestion(question);
        return mapper.convertValue(question, QuestionDto.class);
    }

    @MessageMapping({"/quiz/answer"})
    @SendToUser({"/queue/questions"})
    public Object handleQuizAnswer(@Valid QuestionResponse response, Principal principal) {
        return quizSocketService.putAnswer(response, principal);
    }
}
