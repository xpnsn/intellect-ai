package com.bohemian.intellect.service;

import com.bohemian.intellect.model.Question;
import com.bohemian.intellect.model.QuizSession;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    public void updateSession(QuizSession session, Question question, boolean correct) {
        session.setTotalQuestions(session.getTotalQuestions() + 1);
        if (correct) {
            session.setCorrectCount(session.getCorrectCount() + 1);
        }

        session.getHistory().add(question);

        if (question.getConcept() != null && !question.getConcept().isBlank()) {
            session.getConceptMastery().merge(question.getConcept(), correct ? 1.0 : -0.5, Double::sum);
        }
    }
}

