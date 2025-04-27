package com.tourgether.tourgether.attraction.dto;

import com.tourgether.tourgether.attraction.entity.AttractionTranslation;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "지도용 여행지 요약 응답 DTO")
public record AttractionMapSummaryResponse(
    @Schema(description = "여행지 번역 ID", example = "1")
    Long id,

    @Schema(description = "여행지 이름", example = "경복궁")
    String name,

    @Schema(description = "여행지 주소", example = "서울 종로구 사직로 161")
    String address,

    @Schema(description = "썸네일 이미지 URL", example = "https://example.com/images/gyeongbokgung.jpg")
    String thumbnailImgUrl,

    @Schema(description = "위도", example = "37.579617")
    double latitude,

    @Schema(description = "경도", example = "126.977041")
    double longitude
) {

  public static AttractionMapSummaryResponse from(AttractionTranslation entity) {
    return new AttractionMapSummaryResponse(
        entity.getTranslationId(),
        entity.getName(),
        entity.getAddress(),
        entity.getAttraction().getThumbnailImgUrl(),
        entity.getAttraction().getLocation().getY(),
        entity.getAttraction().getLocation().getX()
    );
  }
}
