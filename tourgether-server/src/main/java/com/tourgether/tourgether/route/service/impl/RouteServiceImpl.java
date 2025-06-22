package com.tourgether.tourgether.route.service.impl;

import com.tourgether.tourgether.route.client.ODsayClient;
import com.tourgether.tourgether.route.dto.response.RouteLaneResponse;
import com.tourgether.tourgether.route.dto.response.RouteSearchResponse;
import com.tourgether.tourgether.route.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

  private final ODsayClient odsayClient;

  @Override
  public RouteSearchResponse searchRoutes(double startX, double startY, double endX, double endY,
      int lang) {
    return odsayClient.searchRoute(startX, startY, endX, endY, lang);
  }

  @Override
  public RouteLaneResponse loadRouteLane(String mapObj, int lang) {
    return odsayClient.loadLane(mapObj, lang);
  }
}
