package com.tourgether.tourgether.attraction.controller;

import com.tourgether.tourgether.attraction.dto.QuizResponse;
import com.tourgether.tourgether.attraction.service.QuizService;
import com.tourgether.tourgether.common.dto.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/attractions")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/{translationId}/quizzes")
    public ResponseEntity<ApiResult<List<QuizResponse>>> getQuizzes(
            @PathVariable Long translationId
    ){
        List<QuizResponse> quizzes = quizService.getAttractionQuizzes(translationId);
        return ResponseEntity.ok(ApiResult.success(quizzes));
    }

}
