package com.tourgether.tourgether.attraction.service;

import com.tourgether.tourgether.attraction.dto.AttractionDetailResponse;
import com.tourgether.tourgether.attraction.dto.AttractionSummaryResponse;

import com.tourgether.tourgether.attraction.dto.LevelDescriptionResponse;
import java.util.List;

public interface AttractionService {

  List<AttractionSummaryResponse> searchAttractions(Long languageId, String keyword);

  List<AttractionSummaryResponse> searchNearbyAttractions(
      double latitude,
      double longitude,
      double radius,
      Long languageId
  );

  AttractionDetailResponse getAttractionDetail(Long translationId);

  List<LevelDescriptionResponse> getAttractionLevelDescriptions(Long translationId);

  List<AttractionSummaryResponse> getPopularAttractions(Long languageId, int limit);
}
