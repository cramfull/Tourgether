package com.tourgether.tourgether.route.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 * ODsay API 최상위 응답 DTO
 */
@Schema(description = "ODsay API 전체 응답")
public record RouteSearchResponse(

    @Schema(description = "ODsay API result 필드")
    Result result

) {

  /**
   * ---------- level-1 : result ----------
   */
  @Schema(description = "ODsay API result 객체")
  public record Result(

      @Schema(description = "검색 유형(0:기본)")
      Integer searchType,

      @Schema(description = "교통 수단 이용 제한 체크(0: 제한 없음)")
      Integer outTrafficCheck,

      @Schema(description = "버스 경로 개수")
      Integer busCount,

      @Schema(description = "지하철 경로 개수")
      Integer subwayCount,

      @Schema(description = "혼합(버스+지하철) 경로 개수")
      Integer subwayBusCount,

      @Schema(description = "시작–도착 직선 거리(단위:m)")
      Integer pointDistance,

      @Schema(description = "출발 반경")
      Integer startRadius,

      @Schema(description = "도착 반경")
      Integer endRadius,

      @Schema(description = "추천 경로 리스트")
      List<Path> path
  ) {

  }

  /**
   * ---------- level-2 : path ----------
   */
  @Schema(description = "추천 경로")
  public record Path(

      @Schema(description = "경로 유형 (1: 지하철, 2: 버스, 3: 혼합)")
      Integer pathType,

      @Schema(description = "경로 요약 정보")
      Info info,

      @Schema(description = "구간별 세부 경로")
      List<SubPath> subPath
  ) {

  }

  /**
   * ---------- level-3 : info ----------
   */
  @Schema(description = "경로 요약 정보")
  public record Info(

      @Schema(description = "mapObj (loadLane 요청용)")
      String mapObj,

      @Schema(description = "총 소요 시간(분)")
      Integer totalTime,

      @Schema(description = "요금")
      Integer payment,

      @Schema(description = "총 도보 거리(m)")
      Integer totalWalk,

      @Schema(description = "총 교통 거리(m)")
      Double trafficDistance,

      @Schema(description = "버스 환승 횟수")
      Integer busTransitCount,

      @Schema(description = "지하철 환승 횟수")
      Integer subwayTransitCount,

      @Schema(description = "출발 정류장/역명")
      String firstStartStation,

      @Schema(description = "도착 정류장/역명")
      String lastEndStation,

      @Schema(description = "전체 정류장 수")
      Integer totalStationCount,

      /* -- 추가 필드 -- */
      @Schema(description = "버스 정류장 수")
      Integer busStationCount,

      @Schema(description = "지하철 역 수")
      Integer subwayStationCount,

      @Schema(description = "총 이동 거리(m)")
      Double totalDistance,

      @Schema(description = "총 도보 시간(분, -1이면 미제공)")
      Integer totalWalkTime,

      @Schema(description = "버스/지하철 평균 배차 간격(초)")
      Integer checkIntervalTime,

      @Schema(description = "배차 간격 초과 여부(Y/N)")
      String checkIntervalTimeOverYn,

      @Schema(description = "전체 평균 배차 간격(분)")
      Integer totalIntervalTime
  ) {

  }

  /**
   * ---------- level-3 : subPath ----------
   */
  @Schema(description = "구간 정보")
  public record SubPath(

      @Schema(description = "교통 수단 (1:지하철, 2:버스, 3:도보)")
      Integer trafficType,

      @Schema(description = "구간 거리(m)")
      Integer distance,

      @Schema(description = "구간 소요 시간(분)")
      Integer sectionTime,

      /* 버스/지하철 공통 */
      @Schema(description = "정류장/역 개수")
      Integer stationCount,

      @Schema(description = "노선 상세 정보 목록")
      List<Lane> lane,

      @Schema(description = "배차 간격(분)")
      Integer intervalTime,

      /* 출발/도착 이름 & 좌표 */
      String startName,
      Double startX,
      Double startY,

      String endName,
      Double endX,
      Double endY,

      /* ID 및 코드들(버스·지하철 구간에만 존재) */
      Long startID,
      Integer startStationCityCode,
      Integer startStationProviderCode,
      String startLocalStationID,
      String startArsID,

      Long endID,
      Integer endStationCityCode,
      Integer endStationProviderCode,
      String endLocalStationID,
      String endArsID,

      /* 지하철 특화 필드 */
      String way,
      Integer wayCode,
      String door,

      String startExitNo,
      Double startExitX,
      Double startExitY,

      String endExitNo,
      Double endExitX,
      Double endExitY,

      /* 정류장/역 리스트 */
      PassStopList passStopList
  ) {

  }

  /**
   * ---------- level-4 : lane ----------
   */
  @Schema(description = "노선 상세 정보(버스 or 지하철 공용)")
  public record Lane(

      /* 버스용 필드 */
      String busNo,
      Integer type,
      Long busID,
      String busLocalBlID,
      Integer busCityCode,
      Integer busProviderCode,

      /* 지하철용 필드 */
      String name,
      Integer subwayCode,
      Integer subwayCityCode
  ) {

  }

  /**
   * ---------- level-4 : passStopList ----------
   */
  @Schema(description = "정류장/역 리스트")
  public record PassStopList(

      @Schema(description = "정류장/역 배열")
      List<Station> stations
  ) {

  }

  /**
   * ---------- level-5 : station ----------
   */
  @Schema(description = "정류장/역 정보")
  public record Station(

      Integer index,
      Long stationID,
      String stationName,
      Double x,
      Double y,

      /* 버스 전용 추가 필드 */
      Integer stationCityCode,
      Integer stationProviderCode,
      String localStationID,
      String arsID,
      String isNonStop
  ) {

  }
}
