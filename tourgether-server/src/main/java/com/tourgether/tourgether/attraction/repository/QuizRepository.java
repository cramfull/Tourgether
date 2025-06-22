package com.tourgether.tourgether.attraction.repository;

import com.tourgether.tourgether.attraction.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByTranslationTranslationId(Long translationId);
}
