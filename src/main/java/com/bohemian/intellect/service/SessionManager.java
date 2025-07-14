package com.bohemian.intellect.service;

import com.bohemian.intellect.model.Question;
import com.bohemian.intellect.model.UserQuizSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {
    private final Map<String, UserQuizSession> sessions = new ConcurrentHashMap<>();

    public void startSession(String username, String quizId, List<Question> questions) {
        this.sessions.put(username, new UserQuizSession(username, quizId, questions, LocalDateTime.now()));
    }

    public UserQuizSession getSession(String username) {
        return sessions.get(username);
    }

    public void removeSession(String username) {
        this.sessions.remove(username);
    }
}
