package com.tourgether.tourgether.attraction.dto;

import com.tourgether.tourgether.attraction.entity.AttractionTranslation;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.locationtech.jts.geom.Point;

@Schema(description = "여행지 상세 응답 DTO")
public record AttractionDetailResponse(

    @Schema(description = "여행지 번역 ID", example = "1")
    Long translationId,

    @Schema(description = "여행지 ID", example = "1")
    Long attractionId,

    @Schema(description = "썸네일 url", example = "1")
    String thumbnailImgUrl,

    @Schema(description = "여행지 이름", example = "경복궁")
    String name,

    @Schema(description = "여행지 주소", example = "서울 종로구 사직로 161")
    String address,

    @Schema(description = "여행지 요약", example = "조선 시대 궁궐로 유명한 관광 명소입니다.")
    String summary,

    @Schema(description = "개장 요일", example = "화요일~일요일")
    String openingDay,

    @Schema(description = "개장 시간", example = "09:00~18:00")
    String openingTime,

    @Schema(description = "휴무일", example = "월요일")
    String closedDay,

    @Schema(description = "오디오 텍스트", example = "경복궁은 조선의 법궁으로, 태조에 의해 세워졌습니다.")
    String audioText,

    @Schema(description = "오디오 URL", example = "https://example.com/audio/gyeongbokgung.mp3")
    String audioUrl,

    @Schema(description = "위도", example = "37.5796")
    BigDecimal latitude,

    @Schema(description = "경도", example = "126.9770")
    BigDecimal longitude

) {

  public static AttractionDetailResponse from(AttractionTranslation entity) {
    Point location = entity.getAttraction().getLocation();

    return new AttractionDetailResponse(
        entity.getTranslationId(),
        entity.getAttraction().getId(),
        entity.getAttraction().getThumbnailImgUrl(),
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