package com.tourgether.tourgether.attraction.dto;

import com.tourgether.tourgether.attraction.entity.AttractionTranslation;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "여행지 요약 응답 DTO")
public record AttractionSummaryResponse(
    @Schema(description = "여행지 번역 ID", example = "1")
    Long id,

    @Schema(description = "여행지 이름", example = "경복궁")
    String name,

    @Schema(description = "여행지 주소", example = "서울 종로구 사직로 161")
    String address,

    @Schema(description = "여행지 요약 설명", example = "조선 시대 궁궐로 유명한 관광 명소입니다.")
    String summary,

    @Schema(description = "썸네일 이미지 URL", example = "https://example.com/images/gyeongbokgung.jpg")
    String thumbnailImgUrl
) {

  public static AttractionSummaryResponse from(AttractionTranslation entity) {
    return new AttractionSummaryResponse(
        entity.getTranslationId(),
        entity.getName(),
        entity.getAddress(),
        entity.getSummary(),
        entity.getAttraction().getThumbnailImgUrl()
    );
  }
}

