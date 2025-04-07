package com.tourgether.tourgether.attraction.dto;

import com.tourgether.tourgether.attraction.entity.LevelDescription;

public record LevelDescriptionResponse(
    Long id,
    String description
) {

  public static LevelDescriptionResponse from(LevelDescription entity) {
    return new LevelDescriptionResponse(
        entity.getId(),
        entity.getDescription()
    );
  }
}
