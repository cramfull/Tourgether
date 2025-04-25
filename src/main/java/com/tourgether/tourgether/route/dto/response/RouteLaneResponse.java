package com.tourgether.tourgether.route.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "경로 상세 선형 좌표 응답 (ODsay mapObj 기반)")
public record RouteLaneResponse(

    @Schema(description = "결과")
    Result result

) {

  @Schema(description = "응답 result 필드")
  public record Result(
      @Schema(description = "구간별 레인 정보")
      List<Lane> lane
  ) {

  }

  @Schema(description = "레인 정보")
  public record Lane(
      @Schema(description = "레인 클래스")
      @JsonProperty("class")
      int clazz,

      @Schema(description = "레인 타입")
      int type,

      @Schema(description = "섹션 리스트")
      List<Section> section
  ) {

  }

  @Schema(description = "섹션")
  public record Section(
      @Schema(description = "좌표 리스트")
      List<GraphPos> graphPos
  ) {

  }

  @Schema(description = "좌표")
  public record GraphPos(
      @Schema(description = "경도")
      double x,

      @Schema(description = "위도")
      double y
  ) {

  }
}
