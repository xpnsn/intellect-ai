package com.bohemian.intellect.model;

import com.bohemian.intellect.dto.QuestionDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class QuizSession {
    private final String username;
    private final String quizId;
    private final LocalDateTime startedAt;
    private final List<Question> questions;
    private final List<String> answers;
    private int currentIndex;

    private String topic;
    private String level;
    private int currentDifficulty;
    private Map<String, Double> conceptMastery;
    private Question lastQuestion;
    private List<Question> history;
    private int correctCount;
    private int totalQuestions;
    private boolean aiMode;

    private QuizSession(String username, String quizId, List<Question> questions, LocalDateTime startedAt) {
        this.username = username;
        this.quizId = quizId;
        this.startedAt = startedAt;
        this.questions = questions;
        this.answers = new ArrayList<>();
        this.currentIndex = 0;
        this.conceptMastery = new HashMap<>();
        this.history = new ArrayList<>();
        this.correctCount = 0;
        this.totalQuestions = 0;
        this.currentDifficulty = 3;
        this.aiMode = false;
    }

    public static QuizSession regular(String username, String quizId, List<Question> questions) {
        return new QuizSession(username, quizId, questions, LocalDateTime.now());
    }

    public static QuizSession ai(String username, String topic, String level) {
        QuizSession session = new QuizSession(username, "ai-quiz", new ArrayList<>(), LocalDateTime.now());
        session.aiMode = true;
        session.topic = topic;
        session.level = level;
        session.currentDifficulty = mapLevelToDifficulty(level);
        return session;
    }

    public QuestionDto getCurrentQuestion() {
        Question current = questions.get(currentIndex);
        return new QuestionDto(current.getTitle(), current.getOptions());
    }

    public boolean putAnswer(String answer) {
        this.answers.add(answer);
        this.currentIndex++;
        return this.currentIndex < this.questions.size();
    }

    public QuizResult evaluateResult() {
        int size = this.questions.size();
        int correctAnswers = 0;
        for (int i = 0; i < size; i++) {
            Question question = questions.get(i);
            if (question.getCorrectAnswer().equals(answers.get(i))) {
                correctAnswers++;
            }
        }
        return new QuizResult(null, quizId, username, size, correctAnswers, answers, startedAt, LocalDateTime.now());
    }

    private static int mapLevelToDifficulty(String level) {
        if (level == null) {
            return 3;
        }
        return switch (level.toLowerCase()) {
            case "beginner" -> 2;
            case "advanced" -> 4;
            default -> 3;
        };
    }
}
