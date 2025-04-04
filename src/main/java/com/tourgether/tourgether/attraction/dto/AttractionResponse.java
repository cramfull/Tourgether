package com.tourgether.tourgether.attraction.dto;

import com.tourgether.tourgether.attraction.entity.AttractionTranslation;

import java.math.BigDecimal;

public record AttractionResponse(
    Long id,
    String name,
    String address,
    String summary,
    String audioUrl,
    String audioText,
    String openingDay,
    String openingTime,
    String closedDay,

    BigDecimal latitude,
    BigDecimal longitude

) {

  public static AttractionResponse from(AttractionTranslation entity) {
    return new AttractionResponse(
        entity.getTranslationId(),
        entity.getName(),
        entity.getAddress(),
        entity.getSummary(),
        entity.getAudioUrl(),
        entity.getAudioText(),
        entity.getOpeningDay(),
        entity.getOpeningTime(),
        entity.getClosedDay(),
        entity.getAttraction().getLatitude(),
        entity.getAttraction().getLongitude()
    );
  }
}
