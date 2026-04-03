package com.bohemian.intellect.service;

import com.bohemian.intellect.dto.GenerateRequest;
import com.bohemian.intellect.dto.QuestionDto;
import com.bohemian.intellect.dto.QuestionResponse;
import com.bohemian.intellect.dto.QuizStartRequest;
import com.bohemian.intellect.model.Question;
import com.bohemian.intellect.model.QuizResult;
import com.bohemian.intellect.model.QuizSession;
import com.bohemian.intellect.repository.ResultRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class QuizSocketService {
    private final QuestionService questionService;
    private final SessionManager sessionManager;
    private final ObjectMapper mapper;
    private final ResultRepository resultRepository;
    private final UserService userService;
    private final AIService aiService;
    private final AdaptiveEngineService adaptiveEngineService;
    private final SessionService sessionService;

    public QuizSocketService(QuestionService questionService,
                             SessionManager sessionManager,
                             ObjectMapper mapper,
                             ResultRepository resultRepository,
                             UserService userService,
                             AIService aiService,
                             AdaptiveEngineService adaptiveEngineService,
                             SessionService sessionService) {
        this.questionService = questionService;
        this.sessionManager = sessionManager;
        this.mapper = mapper;
        this.resultRepository = resultRepository;
        this.userService = userService;
        this.aiService = aiService;
        this.adaptiveEngineService = adaptiveEngineService;
        this.sessionService = sessionService;
    }

    public QuestionDto startQuiz(QuizStartRequest req, Principal principal) {
        String username = principal.getName();
        List<Question> questions = questionService.getRawQuestionFromQuiz(req.quizId());
        sessionManager.startSession(username, req.quizId(), questions);
        return mapper.convertValue(questions.get(0), QuestionDto.class);
    }

    public Object putAnswer(QuestionResponse response, Principal principal) {
        String username = principal.getName();
        QuizSession session = sessionManager.getSession(username);

        if (session == null) {
            throw new IllegalStateException("No active quiz session found");
        }

        if (session.isAiMode()) {
            return handleAdaptiveAnswer(session, response.answer());
        }

        if (session.putAnswer(response.answer())) {
            return session.getCurrentQuestion();
        }

        QuizResult quizResult = session.evaluateResult();
        quizResult = resultRepository.save(quizResult);
        userService.addResultToUser(quizResult.getResultID(), username);
        sessionManager.removeSession(username);
        return quizResult;
    }

    private Map<String, Object> handleAdaptiveAnswer(QuizSession session, String userAnswer) {
        Question last = session.getLastQuestion();
        if (last == null) {
            throw new IllegalStateException("AI session is missing the previous question");
        }

        boolean wasCorrect = last.getCorrectAnswer() != null
            && last.getCorrectAnswer().trim().equalsIgnoreCase(userAnswer == null ? "" : userAnswer.trim());

        sessionService.updateSession(session, last, wasCorrect);

        int nextDifficulty = Math.max(1, Math.min(5, session.getCurrentDifficulty() + (wasCorrect ? 1 : -1)));
        session.setCurrentDifficulty(nextDifficulty);

        GenerateRequest request = adaptiveEngineService.buildRequest(session, last, userAnswer, wasCorrect);
        Question nextQuestion = aiService.generateQuestion(request);
        session.setLastQuestion(nextQuestion);

        String explanationPrefix = wasCorrect ? "Correct. " : "Incorrect. ";
        String explanation = explanationPrefix + (last.getExplanation() == null ? "Review the underlying concept and try again." : last.getExplanation());

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("previousCorrect", wasCorrect);
        payload.put("explanation", explanation);
        payload.put("concept", last.getConcept());
        payload.put("nextQuestion", mapper.convertValue(nextQuestion, QuestionDto.class));
        payload.put("mastery", session.getConceptMastery());
        return payload;
    }
}
