package com.tourgether.tourgether.visit.dto.response;

import com.tourgether.tourgether.attraction.entity.AttractionTranslation;
import com.tourgether.tourgether.visit.entity.Visit;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public record VisitResponse(
    Long visitId,
    Long attractionId,
    String name,
    String address,
    String thumbnailImgUrl,
    String visitedAt
) {

  private static final DateTimeFormatter KOREAN_FORMATTER = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd HH:mm").withZone(
      ZoneId.of("Asia/Seoul"));

  public static VisitResponse from(Visit visit, AttractionTranslation translation) {
    return new VisitResponse(
        visit.getId(),
        visit.getAttraction().getId(),
        translation.getName(),
        translation.getAddress(),
        visit.getAttraction().getThumbnailImgUrl(),
        KOREAN_FORMATTER.format(visit.getVisitedAt().atZone(ZoneId.systemDefault()))
    );
  }
}
