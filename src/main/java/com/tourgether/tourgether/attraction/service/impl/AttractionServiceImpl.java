package com.tourgether.tourgether.attraction.service.impl;

import com.tourgether.tourgether.attraction.dto.AttractionDetailResponse;
import com.tourgether.tourgether.attraction.dto.AttractionMapSummaryResponse;
import com.tourgether.tourgether.attraction.dto.AttractionSummaryResponse;
import com.tourgether.tourgether.attraction.dto.LevelDescriptionResponse;
import com.tourgether.tourgether.attraction.entity.AttractionTranslation;
import com.tourgether.tourgether.attraction.exception.AttractionTranslationNotFoundException;
import com.tourgether.tourgether.attraction.repository.AttractionTranslationRepository;
import com.tourgether.tourgether.attraction.repository.LevelDescriptionRepository;
import com.tourgether.tourgether.attraction.service.AttractionService;
import com.tourgether.tourgether.language.entity.Language;
import com.tourgether.tourgether.language.exception.LanguageNotFoundException;
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
  public List<AttractionSummaryResponse> searchAttractions(Long languageId, String keyword) {

    Language language = languageRepository.findById(languageId)
        .orElseThrow(() -> new LanguageNotFoundException("존재하지 않는 언어입니다."));

    return translationRepository.searchByKeywordInFields(language, keyword)
        .stream()
        .map(AttractionSummaryResponse::from)
        .toList();
  }

  @Override
  public List<AttractionSummaryResponse> searchNearbyAttractions(double latitude, double longitude,
      double radius, Long languageId) {

    return translationRepository.findNearbyAttractionsByLanguageId(latitude, longitude, radius,
            languageId)
        .stream()
        .map(AttractionSummaryResponse::from)
        .toList();
  }

  @Override
  public AttractionDetailResponse getAttractionDetail(Long translationId) {
    AttractionTranslation translation = translationRepository.findById(translationId)
        .orElseThrow(
            () -> new AttractionTranslationNotFoundException("해당 번역 ID의 여행지 정보를 찾을 수 없습니다."));
    return AttractionDetailResponse.from(translation);
  }

  @Override
  public List<LevelDescriptionResponse> getAttractionLevelDescriptions(Long translationId) {

    boolean exists = translationRepository.existsById(translationId);
    if (!exists) {
      throw new AttractionTranslationNotFoundException("해당 번역 ID의 여행지 정보를 찾을 수 없습니다.");
    }

    return levelDescriptionRepository
        .findByTranslationTranslationId(translationId)
        .stream()
        .map(LevelDescriptionResponse::from)
        .toList();
  }

  @Override
  public List<AttractionSummaryResponse> getPopularAttractions(Long languageId, int limit) {
    boolean exists = languageRepository.existsById(languageId);
    if (!exists) {
      throw new AttractionTranslationNotFoundException("존재하지 않는 언어입니다.");
    }

    return translationRepository.findTopVisitedAttractions(languageId, limit)
        .stream()
        .map(AttractionSummaryResponse::from)
        .toList();
  }

  @Override
  public List<AttractionMapSummaryResponse> getAttractionsWithinBounds(
      Long languageId, double swLat, double swLng, double neLat, double neLng) {

    boolean exists = languageRepository.existsById(languageId);
    if (!exists) {
      throw new AttractionTranslationNotFoundException("존재하지 않는 언어입니다.");
    }

    return translationRepository.findByTranslationIdMapBounds(
            swLat, swLng, neLat, neLng, languageId)
        .stream()
        .map(AttractionMapSummaryResponse::from)
        .toList();
  }
}
