package com.tourgether.tourgether.attraction.service;

import com.tourgether.tourgether.attraction.dto.AttractionLikeResponse;
import java.util.List;

public interface AttractionLikeService {

  boolean toggleLike(Long attractionId, Long memberId);

  boolean isLiked(Long attractionId, Long memberId);

  List<AttractionLikeResponse> getMyLikedAttractions(Long memberId);
}
