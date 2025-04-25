package com.tourgether.tourgether.route.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "ODsay API 응답 최상단 구조")
public record RouteSearchResponse(
    @Schema(description = "결과 데이터")
    Result result
) {

  @Schema(description = "ODsay API 응답 result 필드")
  public record Result(
      @Schema(description = "추천 경로 리스트")
      List<Path> path
  ) {

  }

  @Schema(description = "추천 경로")
  public record Path(
      @Schema(description = "경로 유형 (예: 1:지하철, 2:버스, 3:혼합)")
      int pathType,

      @Schema(description = "요약 정보")
      Info info,

      @Schema(description = "구간별 세부 경로")
      List<SubPath> subPath
  ) {

  }

  @Schema(description = "경로 요약 정보")
  public record Info(
      @Schema(description = "mapObj 값 (loadLane 요청용)")
      String mapObj,

      @Schema(description = "총 소요 시간")
      int totalTime,

      @Schema(description = "요금")
      int payment,

      @Schema(description = "도보 거리")
      int totalWalk,

      @Schema(description = "교통 거리")
      double trafficDistance,

      @Schema(description = "버스 환승 횟수")
      int busTransitCount,

      @Schema(description = "지하철 환승 횟수")
      int subwayTransitCount,

      @Schema(description = "출발지")
      String firstStartStation,

      @Schema(description = "도착지")
      String lastEndStation,

      @Schema(description = "전체 정류장 수")
      int totalStationCount
  ) {

  }

  @Schema(description = "구간 정보")
  public record SubPath(
      @Schema(description = "교통 수단 유형 (1:지하철, 2:버스, 3:도보)")
      int trafficType,

      @Schema(description = "구간 시간")
      int sectionTime,

      @Schema(description = "구간 거리")
      int distance,

      @Schema(description = "출발 정류장")
      String startName,

      @Schema(description = "도착 정류장")
      String endName,

      @Schema(description = "정류장 리스트")
      PassStopList passStopList
  ) {

  }

  @Schema(description = "정류장 리스트")
  public record PassStopList(
      @Schema(description = "정류장 배열")
      List<Station> stations
  ) {

  }

  @Schema(description = "정류장")
  public record Station(
      @Schema(description = "순서")
      int index,

      @Schema(description = "정류장 ID")
      long stationID,

      @Schema(description = "정류장 이름")
      String stationName,

      @Schema(description = "경도")
      double x,

      @Schema(description = "위도")
      double y
  ) {

  }
}
