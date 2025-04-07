package com.tourgether.tourgether.attraction.service.impl;

import com.tourgether.tourgether.attraction.dto.AttractionDetailResponse;
import com.tourgether.tourgether.attraction.dto.AttractionResponse;
import com.tourgether.tourgether.attraction.dto.LevelDescriptionResponse;
import com.tourgether.tourgether.attraction.entity.AttractionTranslation;
import com.tourgether.tourgether.attraction.repository.AttractionTranslationRepository;
import com.tourgether.tourgether.attraction.repository.LevelDescriptionRepository;
import com.tourgether.tourgether.attraction.service.AttractionService;
import com.tourgether.tourgether.language.entity.Language;
import com.tourgether.tourgether.language.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttractionServiceImpl implements AttractionService {

  private final AttractionTranslationRepository translationRepository;
  private final LanguageRepository languageRepository;
  private final LevelDescriptionRepository levelDescriptionRepository;

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

  @Override
  public AttractionDetailResponse getAttractionDetail(Long attractionId, Long languageId) {

    AttractionTranslation attractionTranslation = translationRepository.findByAttractionIdAndLanguageId(
            attractionId, languageId)
        .orElseThrow(() -> new IllegalArgumentException("해당 언어의 여행지 정보를 찾을 수 없습니다."));

    return AttractionDetailResponse.from(attractionTranslation);
  }

  @Override
  public List<LevelDescriptionResponse> getAttractionLevelDescriptions(Long attractionId,
      Long languageId) {
    
    return levelDescriptionRepository
        .findByTranslationAttractionIdAndTranslationLanguageId(attractionId, languageId)
        .stream()
        .map(LevelDescriptionResponse::from)
        .toList();
  }
}
