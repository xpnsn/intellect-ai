//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.bohemian.intellect.model;

import com.bohemian.intellect.dto.QuestionDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserQuizSession {
    private final String username;
    private final String quizId;
    private final List<Question> questions;
    private int currentIndex;
    private List<String> answers;
    private final LocalDateTime startedAt;

    public UserQuizSession(String username, String quizId, List<Question> questions, LocalDateTime startedAt) {
        this.username = username;
        this.quizId = quizId;
        this.startedAt = startedAt;
        this.currentIndex = 0;
        this.questions = questions;
        this.answers = new ArrayList<>();
    }

    public QuestionDto getCurrentQuestion() {
        return new QuestionDto((questions.get(currentIndex)).getTitle(), (questions.get(currentIndex)).getOptions());
    }

    public boolean putAnswer(String answer) {
        this.answers.add(answer);
        ++this.currentIndex;
        return this.currentIndex < this.questions.size();
    }

    public QuizResult evaluateResult() {
        int size = this.questions.size();
        int correctAnswers = 0;

        for(int i = 0; i < size; ++i) {
            Question question = questions.get(i);
            if (question.getCorrectAnswer().equals(answers.get(i))) {
                ++correctAnswers;
            }
        }

        return new QuizResult(null, quizId, username, size, correctAnswers, answers, startedAt, LocalDateTime.now());
    }
}