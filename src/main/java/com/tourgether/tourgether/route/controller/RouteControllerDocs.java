package com.tourgether.tourgether.route.controller;

import com.tourgether.tourgether.common.dto.ApiResult;
import com.tourgether.tourgether.common.dto.ExceptionResponse;
import com.tourgether.tourgether.route.dto.response.RouteLaneResponse;
import com.tourgether.tourgether.route.dto.response.RouteSearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Route", description = "대중교통 경로 API")
public interface RouteControllerDocs {

  @Operation(summary = "대중교통 경로 검색", description = "ODsay 경로 탐색 API 호출")
  @ApiResponse(responseCode = "200", description = "정상적으로 조회됨")
  @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
  @ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
  ResponseEntity<ApiResult<RouteSearchResponse>> searchRoute(
      @Parameter(description = "출발지 X좌표", example = "126.972314") double startX,
      @Parameter(description = "출발지 Y좌표", example = "37.555942") double startY,
      @Parameter(description = "도착지 X좌표", example = "126.973557") double endX,
      @Parameter(description = "도착지 Y좌표", example = "37.575875") double endY,
      @Parameter(description = "언어 코드", example = "0") int lang
  );

  @Operation(summary = "경로 폴리라인 조회", description = "ODsay mapObj 기반 경로 좌표 조회")
  @ApiResponse(responseCode = "200", description = "정상적으로 조회됨")
  @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
  @ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
  ResponseEntity<ApiResult<RouteLaneResponse>> loadLane(
      @Parameter(description = "mapObj 문자열", example = "3:2:329:327") String mapObj,
      @Parameter(description = "언어 코드", example = "0") int lang
  );
}
