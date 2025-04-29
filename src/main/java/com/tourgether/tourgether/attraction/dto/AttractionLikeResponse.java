package com.tourgether.tourgether.attraction.dto;

import com.tourgether.tourgether.attraction.entity.Attraction;
import com.tourgether.tourgether.attraction.entity.AttractionTranslation;

public record AttractionLikeResponse(
    Long attractionId,
    String name,
    String address,
    String thumbnailImgUrl
) {

  public static AttractionLikeResponse from(Attraction attraction,
      AttractionTranslation translation) {
    return new AttractionLikeResponse(
        attraction.getId(),
        translation.getName(),
        translation.getAddress(),
        attraction.getThumbnailImgUrl()
    );
  }
}
