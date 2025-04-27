package com.tourgether.tourgether.attraction.controller;

import com.tourgether.tourgether.attraction.dto.AttractionDetailResponse;
import com.tourgether.tourgether.attraction.dto.AttractionMapSummaryResponse;
import com.tourgether.tourgether.attraction.dto.AttractionSummaryResponse;
import com.tourgether.tourgether.attraction.dto.LevelDescriptionResponse;
import com.tourgether.tourgether.common.dto.ApiResult;
import com.tourgether.tourgether.common.dto.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Attraction", description = "여행지 관련 API")
public interface AttractionControllerDocs {

  @Operation(summary = "여행지 전체 조회", description = "언어 ID와 키워드로 여행지 리스트를 조회합니다.")
  @ApiResponse(responseCode = "200", description = "정상적으로 조회됨")
  @ApiResponse(
      responseCode = "400",
      description = "잘못된 요청 파라미터",
      content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
  )
  @ApiResponse(
      responseCode = "404",
      description = "해당 언어 ID가 존재하지 않음",
      content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
  )
  @ApiResponse(
      responseCode = "500",
      description = "서버 내부 오류",
      content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
  )
  ResponseEntity<ApiResult<List<AttractionSummaryResponse>>> getAttractions(
      @Parameter(description = "언어 ID", example = "1") Long languageId,
      @Parameter(description = "검색 키워드", example = "경복궁") String keyword
  );


  @Operation(summary = "근처 여행지 조회", description = "위도, 경도, 반경과 언어 ID로 주변 여행지를 조회합니다.")
  @ApiResponse(responseCode = "200", description = "정상적으로 조회됨")
  @ApiResponse(
      responseCode = "400",
      description = "잘못된 요청 (예: 반경이 1m 미만일 경우)",
      content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
  )
  @ApiResponse(
      responseCode = "404",
      description = "해당 언어 ID가 존재하지 않음",
      content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
  )
  @ApiResponse(
      responseCode = "500",
      description = "서버 내부 오류",
      content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
  )
  ResponseEntity<ApiResult<List<AttractionSummaryResponse>>> getNearbyAttractions(
      @Parameter(description = "위도", example = "37.5700") double latitude,
      @Parameter(description = "경도", example = "126.9830") double longitude,
      @Parameter(description = "반경(m)", example = "1000") @Min(value = 1, message = "반경은 1m 이상이어야 합니다.") double radius,
      @Parameter(description = "언어 ID", example = "1") Long languageId
  );


  @Operation(summary = "여행지 상세 조회", description = "번역 ID로 상세 정보를 조회합니다.")
  @ApiResponse(responseCode = "200", description = "정상적으로 조회됨")
  @ApiResponse(
      responseCode = "404",
      description = "해당 번역 ID의 여행지 정보를 찾을 수 없습니다.",
      content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
  )
  @ApiResponse(
      responseCode = "500",
      description = "서버 내부 오류",
      content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
  )
  ResponseEntity<ApiResult<AttractionDetailResponse>> getAttractionDetail(
      @Parameter(description = "번역 ID", example = "1") Long translationId
  );


  @Operation(summary = "단계별 설명 조회", description = "여행지 번역 ID를 기반으로 구간별 여행지 설명을 조회합니다.")
  @ApiResponse(responseCode = "200", description = "정상적으로 조회됨")
  @ApiResponse(
      responseCode = "404",
      description = "해당 번역 ID가 존재하지 않음",
      content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
  )
  @ApiResponse(
      responseCode = "500",
      description = "서버 내부 오류",
      content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
  )
  ResponseEntity<ApiResult<List<LevelDescriptionResponse>>> getLevelDescriptions(
      @Parameter(description = "번역 ID", example = "1") Long translationId
  );


  @Operation(summary = "인기 관광지 조회", description = "방문 수 기준으로 인기 많은 관광지를 조회합니다.")
  @ApiResponse(responseCode = "200", description = "정상적으로 조회됨")
  @ApiResponse(
      responseCode = "400",
      description = "limit이 1 미만일 경우",
      content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
  )
  @ApiResponse(
      responseCode = "404",
      description = "해당 언어 ID가 존재하지 않음",
      content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
  )
  @ApiResponse(
      responseCode = "500",
      description = "서버 내부 오류",
      content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
  )
  ResponseEntity<ApiResult<List<AttractionSummaryResponse>>> getPopularAttractions(
      @Parameter(description = "언어 ID", example = "1") Long languageId,
      @Parameter(description = "최대 개수", example = "10") @Min(value = 1, message = "limit은 1 이상이어야 합니다.") int limit
  );

  @Operation(summary = "지도 범위 내 여행지 조회", description = "지도 화면 내 (남서, 북동 좌표 범위)와 언어 ID를 기반으로 여행지를 조회합니다.")
  @ApiResponse(responseCode = "200", description = "정상적으로 조회됨")
  @ApiResponse(
      responseCode = "400",
      description = "잘못된 요청 파라미터",
      content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
  )
  @ApiResponse(
      responseCode = "404",
      description = "해당 언어 ID가 존재하지 않음",
      content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
  )
  @ApiResponse(
      responseCode = "500",
      description = "서버 내부 오류",
      content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
  )
  ResponseEntity<ApiResult<List<AttractionMapSummaryResponse>>> getAttractionsWithinBounds(
      @Parameter(description = "남서쪽 위도", example = "37.5642") double swLat,
      @Parameter(description = "남서쪽 경도", example = "126.9758") double swLng,
      @Parameter(description = "북동쪽 위도", example = "37.5796") double neLat,
      @Parameter(description = "북동쪽 경도", example = "126.9905") double neLng,
      @Parameter(description = "언어 ID", example = "1") Long languageId
  );
}
