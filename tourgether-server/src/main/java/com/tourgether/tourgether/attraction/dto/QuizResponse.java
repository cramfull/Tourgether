package com.tourgether.tourgether.attraction.dto;

import com.tourgether.tourgether.attraction.entity.Quiz;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuizResponse(
        @NotNull
        Long id,
        @NotBlank
        String question,
        @NotNull
        Boolean answer
) {
    public static QuizResponse from(Quiz entity) {
        return new QuizResponse(
                entity.getId(),
                entity.getQuestion(),
                entity.getAnswer()
        );
    }
}
