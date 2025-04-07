package com.tourgether.tourgether.attraction.service;

import com.tourgether.tourgether.attraction.dto.AttractionDetailResponse;
import com.tourgether.tourgether.attraction.dto.AttractionResponse;

import com.tourgether.tourgether.attraction.dto.LevelDescriptionResponse;
import java.util.List;

public interface AttractionService {

  List<AttractionResponse> searchAttractions(Long languageId, String keyword);

  List<AttractionResponse> searchNearbyAttractions(
      double latitude,
      double longitude,
      double radius,
      Long languageId
  );

  AttractionDetailResponse getAttractionDetail(Long attractionId, Long languageId);

  List<LevelDescriptionResponse> getAttractionLevelDescriptions(Long attractionId, Long languageId);
}
