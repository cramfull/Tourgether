package com.tourgether.tourgether.attraction.controller;

import com.tourgether.tourgether.attraction.dto.AttractionResponse;
import com.tourgether.tourgether.attraction.service.AttractionService;
import com.tourgether.tourgether.common.dto.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/attractions")
@RequiredArgsConstructor
public class AttractionController {

  private final AttractionService attractionService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<AttractionResponse>>> getAttractions(
      @RequestParam("lang") Long languageId,
      @RequestParam(value = "keyword", required = false) String keyword
  ) {
    List<AttractionResponse> attractions = attractionService.searchAttractions(languageId, keyword);

    return ResponseEntity.ok(ApiResponse.success(attractions));
  }

  @GetMapping("/nearby")
  public ResponseEntity<ApiResponse<List<AttractionResponse>>> getNearbyAttractions(
      @RequestParam double latitude,
      @RequestParam double longitude,
      @RequestParam double radius,
      @RequestParam Long languageId
  ) {
    List<AttractionResponse> nearbyAttractions = attractionService.searchNearbyAttractions(
        latitude, longitude, radius, languageId);

    return ResponseEntity.ok(ApiResponse.success(nearbyAttractions));
  }
}
