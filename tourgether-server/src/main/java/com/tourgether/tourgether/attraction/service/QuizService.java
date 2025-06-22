package com.tourgether.tourgether.attraction.service;

import com.tourgether.tourgether.attraction.dto.QuizResponse;

import java.util.List;

public interface QuizService {
    List<QuizResponse> getAttractionQuizzes(Long translationId);
}
