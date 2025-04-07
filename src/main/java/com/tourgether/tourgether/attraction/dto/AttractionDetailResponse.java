package com.tourgether.tourgether.attraction.dto;

import com.tourgether.tourgether.attraction.entity.AttractionTranslation;
import java.math.BigDecimal;
import org.locationtech.jts.geom.Point;

public record AttractionDetailResponse(
    Long id,
    String name,
    String address,
    String summary,
    String openingDay,
    String openingTime,
    String closedDay,
    String audioText,
    String audioUrl,

    BigDecimal latitude,
    BigDecimal longitude
) {

  public static AttractionDetailResponse from(AttractionTranslation entity) {
    Point location = entity.getAttraction().getLocation();

    return new AttractionDetailResponse(
        entity.getTranslationId(),
        entity.getName(),
        entity.getAddress(),
        entity.getSummary(),
        entity.getOpeningDay(),
        entity.getOpeningTime(),
        entity.getClosedDay(),
        entity.getAudioText(),
        entity.getAudioUrl(),
        BigDecimal.valueOf(location.getY()),
        BigDecimal.valueOf(location.getX())
    );
  }
}
