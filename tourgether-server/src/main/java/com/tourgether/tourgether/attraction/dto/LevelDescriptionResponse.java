package com.tourgether.tourgether.attraction.dto;

import com.tourgether.tourgether.attraction.entity.LevelDescription;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "여행지 단계별 설명 응답 DTO")
public record LevelDescriptionResponse(
    @Schema(description = "단계 ID", example = "1")
    Long id,

    @Schema(description = "특정 구간에서 제공되는 관광지 요약 설명", example = "경복궁은 조선의 정궁으로, 웅장한 궁궐 구조를 자랑합니다.")
    String description
) {

  public static LevelDescriptionResponse from(LevelDescription entity) {
    return new LevelDescriptionResponse(
        entity.getId(),
        entity.getDescription()
    );
  }
}
