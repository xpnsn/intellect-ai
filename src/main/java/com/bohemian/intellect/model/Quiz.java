package com.bohemian.intellect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name = "quiz_table")
public class Quiz {
    @Id
    @GeneratedValue(
        strategy = GenerationType.UUID
    )
    private String id;
    private String title;
    private String description;
    private String username;
    private List<Long> questionId;

    public Quiz(String id, String title, String description, String username, List<Long> questionId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.username = username;
        this.questionId = questionId;
    }

    public Quiz() {
    }
}
