package com.bohemian.intellect.service;

import com.bohemian.intellect.model.Question;
import com.bohemian.intellect.model.QuizSession;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {
    private final Map<String, QuizSession> sessions = new ConcurrentHashMap<>();

    public void startSession(String username, String quizId, List<Question> questions) {
        this.sessions.put(username, QuizSession.regular(username, quizId, questions));
    }

    public QuizSession startAiSession(String username, String topic, String level) {
        QuizSession session = QuizSession.ai(username, topic, level);
        this.sessions.put(username, session);
        return session;
    }

    public QuizSession getSession(String username) {
        return sessions.get(username);
    }

    public void removeSession(String username) {
        this.sessions.remove(username);
    }
}
