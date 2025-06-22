package com.tourgether.tourgether.attraction.service.impl;

import com.tourgether.tourgether.attraction.dto.QuizResponse;
import com.tourgether.tourgether.attraction.exception.AttractionNotFoundException;
import com.tourgether.tourgether.attraction.exception.AttractionTranslationNotFoundException;
import com.tourgether.tourgether.attraction.repository.AttractionTranslationRepository;
import com.tourgether.tourgether.attraction.repository.QuizRepository;
import com.tourgether.tourgether.attraction.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizServiceImpl implements QuizService {

    private final AttractionTranslationRepository translationRepository;
    private final QuizRepository quizRepository;

    @Override
    public List<QuizResponse> getAttractionQuizzes(Long translationId) {

        boolean exists = translationRepository.existsById(translationId);
        if (!exists) {
            throw new AttractionTranslationNotFoundException("해당 번역 ID의 여행지 정보를 찾을 수 없습니다.");
        }

        return quizRepository.findByTranslationTranslationId(translationId)
                .stream()
                .map(QuizResponse::from)
                .toList();
    }
}
