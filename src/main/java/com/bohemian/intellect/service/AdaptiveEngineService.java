package com.bohemian.intellect.service;

import com.bohemian.intellect.dto.GenerateRequest;
import com.bohemian.intellect.model.Question;
import com.bohemian.intellect.model.QuizSession;
import org.springframework.stereotype.Service;

@Service
public class AdaptiveEngineService {

    public GenerateRequest buildRequest(QuizSession session, Question last, String userAnswer, boolean wasCorrect) {
        GenerateRequest request = new GenerateRequest();
        request.setTopic(session.getTopic());
        request.setMode(wasCorrect ? "followup or new topic" : "remedial or other fundamentals");
        request.setLastQuestion(last.getTitle());
        request.setInitialDifficulty(session.getLevel());
        request.setUserAnswer(userAnswer);
        request.setWasCorrect(wasCorrect);
        request.setTargetConcept(last.getConcept() == null ? session.getTopic() : last.getConcept());
        request.setDifficultyShift(wasCorrect ? 1 : -1);
        return request;
    }
}

