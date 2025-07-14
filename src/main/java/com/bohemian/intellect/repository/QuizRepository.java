//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.bohemian.intellect.repository;

import com.bohemian.intellect.model.Quiz;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, String> {
    List<Quiz> findAllByUsername(String username);
}
