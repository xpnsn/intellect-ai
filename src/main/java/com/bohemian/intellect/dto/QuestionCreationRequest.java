//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.bohemian.intellect.dto;

import java.util.List;

public record QuestionCreationRequest(String title, List<String> options, String correctAnswer, String quizId) {}
