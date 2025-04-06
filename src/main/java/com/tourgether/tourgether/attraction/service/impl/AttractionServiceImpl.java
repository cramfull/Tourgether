package com.tourgether.tourgether.attraction.service.impl;

import com.tourgether.tourgether.attraction.dto.AttractionResponse;
import com.tourgether.tourgether.attraction.repository.AttractionTranslationRepository;
import com.tourgether.tourgether.attraction.service.AttractionService;
import com.tourgether.tourgether.language.entity.Language;
import com.tourgether.tourgether.language.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

  private final AttractionTranslationRepository translationRepository;
  private final LanguageRepository languageRepository;

  @Override
  public List<AttractionResponse> searchAttractions(Long languageId, String keyword) {

    // TODO: custom exception
    Language language = languageRepository.findById(languageId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 언어입니다."));

    return translationRepository.searchByKeywordInFields(language, keyword)
        .stream()
        .map(AttractionResponse::from)
        .toList();
  }

  @Override
  public List<AttractionResponse> searchNearbyAttractions(double latitude, double longitude,
      double radius, Long languageId) {

    return translationRepository.findNearbyAttractionsByLanguageId(latitude, longitude, radius,
            languageId)
        .stream()
        .map(AttractionResponse::from)
        .toList();
  }
}
