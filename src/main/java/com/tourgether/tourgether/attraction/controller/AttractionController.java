package com.tourgether.tourgether.attraction.controller;

import com.tourgether.tourgether.attraction.dto.AttractionDetailResponse;
import com.tourgether.tourgether.attraction.dto.AttractionMapSummaryResponse;
import com.tourgether.tourgether.attraction.dto.AttractionSummaryResponse;
import com.tourgether.tourgether.attraction.dto.LevelDescriptionResponse;
import com.tourgether.tourgether.attraction.service.AttractionService;
import com.tourgether.tourgether.common.dto.ApiResult;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/attractions")
@RequiredArgsConstructor
public class AttractionController implements AttractionControllerDocs {

  private final AttractionService attractionService;

  @GetMapping
  public ResponseEntity<ApiResult<List<AttractionSummaryResponse>>> getAttractions(
      @RequestParam("lang") Long languageId,
      @RequestParam(value = "keyword", required = false) String keyword
  ) {
    List<AttractionSummaryResponse> attractions = attractionService.searchAttractions(languageId,
        keyword);

    return ResponseEntity.ok(ApiResult.success(attractions));
  }

  @GetMapping("/nearby")
  public ResponseEntity<ApiResult<List<AttractionSummaryResponse>>> getNearbyAttractions(
      @RequestParam double latitude,
      @RequestParam double longitude,
      @RequestParam @Min(value = 1, message = "반경은 1m 이상이어야 합니다.") double radius,
      @RequestParam Long languageId
  ) {
    List<AttractionSummaryResponse> nearbyAttractions = attractionService.searchNearbyAttractions(
        latitude, longitude, radius, languageId);

    return ResponseEntity.ok(ApiResult.success(nearbyAttractions));
  }

  @GetMapping("/{translationId}")
  public ResponseEntity<ApiResult<AttractionDetailResponse>> getAttractionDetail(
      @PathVariable Long translationId
  ) {
    AttractionDetailResponse detail = attractionService.getAttractionDetail(
        translationId);
    return ResponseEntity.ok(ApiResult.success(detail));
  }

  @GetMapping("/{translationId}/levels")
  public ResponseEntity<ApiResult<List<LevelDescriptionResponse>>> getLevelDescriptions(
      @PathVariable Long translationId
  ) {
    List<LevelDescriptionResponse> descriptions = attractionService.getAttractionLevelDescriptions(
        translationId);

    return ResponseEntity.ok(ApiResult.success(descriptions));
  }

  @GetMapping("/popular")
  public ResponseEntity<ApiResult<List<AttractionSummaryResponse>>> getPopularAttractions(
      @RequestParam("languageId") Long languageId,
      @RequestParam(value = "limit", defaultValue = "10") @Min(value = 1, message = "limit은 1 이상이어야 합니다.") int limit
  ) {
    List<AttractionSummaryResponse> recommendations =
        attractionService.getPopularAttractions(languageId, limit);

    return ResponseEntity.ok(ApiResult.success(recommendations));
  }

  @GetMapping("/bounds")
  public ResponseEntity<ApiResult<List<AttractionMapSummaryResponse>>> getAttractionsWithinBounds(
      @RequestParam double swLat,
      @RequestParam double swLng,
      @RequestParam double neLat,
      @RequestParam double neLng,
      @RequestParam Long languageId
  ) {
    List<AttractionMapSummaryResponse> attractions = attractionService.getAttractionsWithinBounds(
        languageId, swLat, swLng, neLat, neLng
    );

    return ResponseEntity.ok(ApiResult.success(attractions));
  }

}
