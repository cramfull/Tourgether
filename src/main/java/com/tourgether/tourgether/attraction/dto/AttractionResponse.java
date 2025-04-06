package com.tourgether.tourgether.attraction.dto;

import com.tourgether.tourgether.attraction.entity.AttractionTranslation;

import java.math.BigDecimal;
import org.locationtech.jts.geom.Point;

public record AttractionResponse(
    Long id,
    String name,
    String address,
    String summary,
    String openingDay,
    String openingTime,
    String closedDay,

    BigDecimal latitude,
    BigDecimal longitude

) {

  public static AttractionResponse from(AttractionTranslation entity) {
    Point location = entity.getAttraction().getLocation();

    return new AttractionResponse(
        entity.getTranslationId(),
        entity.getName(),
        entity.getAddress(),
        entity.getSummary(),
        entity.getOpeningDay(),
        entity.getOpeningTime(),
        entity.getClosedDay(),
        BigDecimal.valueOf(location.getY()),
        BigDecimal.valueOf(location.getX())
    );
  }
}
