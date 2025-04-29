package com.tourgether.tourgether.attraction.controller;

import com.tourgether.tourgether.attraction.dto.AttractionLikeResponse;
import com.tourgether.tourgether.attraction.service.AttractionLikeService;
import com.tourgether.tourgether.auth.CustomUserDetails;
import com.tourgether.tourgether.common.dto.ApiResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attractions")
@RequiredArgsConstructor
public class AttractionLikeController {

  private final AttractionLikeService attractionLikeService;

  @PostMapping("/{attractionId}/like/toggle")
  public ResponseEntity<ApiResult<Boolean>> toggleLike(
      @PathVariable Long attractionId,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {
    boolean result = attractionLikeService.toggleLike(attractionId, userDetails.memberId());
    return ResponseEntity.ok(ApiResult.success(result));
  }

  @GetMapping("/likes/me")
  public ResponseEntity<ApiResult<List<AttractionLikeResponse>>> getMyLikedAttractions(
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {
    List<AttractionLikeResponse> likedAttractions =
        attractionLikeService.getMyLikedAttractions(userDetails.memberId());
    return ResponseEntity.ok(ApiResult.success(likedAttractions));
  }

  @GetMapping("/{attractionId}/like")
  public ResponseEntity<ApiResult<Boolean>> isLiked(
      @PathVariable Long attractionId,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {
    boolean result = attractionLikeService.isLiked(attractionId, userDetails.memberId());
    return ResponseEntity.ok(ApiResult.success(result));
  }

}
