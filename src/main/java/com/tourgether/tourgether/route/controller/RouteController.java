package com.tourgether.tourgether.route.controller;

import com.tourgether.tourgether.common.dto.ApiResult;
import com.tourgether.tourgether.route.dto.response.RouteLaneResponse;
import com.tourgether.tourgether.route.dto.response.RouteSearchResponse;
import com.tourgether.tourgether.route.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/route")
@RequiredArgsConstructor
public class RouteController implements RouteControllerDocs {

  private final RouteService routeService;

  @GetMapping("/search")
  public ResponseEntity<ApiResult<RouteSearchResponse>> searchRoute(
      @RequestParam double startX,
      @RequestParam double startY,
      @RequestParam double endX,
      @RequestParam double endY,
      @RequestParam(defaultValue = "0") int lang
  ) {
    RouteSearchResponse response = routeService.searchRoutes(startX, startY, endX, endY, lang);
    return ResponseEntity.ok(ApiResult.success(response));
  }

  @GetMapping("/lane")
  public ResponseEntity<ApiResult<RouteLaneResponse>> loadLane(
      @RequestParam String mapObj,
      @RequestParam(defaultValue = "0") int lang
  ) {
    RouteLaneResponse response = routeService.loadRouteLane(mapObj, lang);
    return ResponseEntity.ok(ApiResult.success(response));
  }
}
