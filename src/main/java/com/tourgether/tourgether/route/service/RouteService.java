package com.tourgether.tourgether.route.service;

import com.tourgether.tourgether.route.dto.response.RouteLaneResponse;
import com.tourgether.tourgether.route.dto.response.RouteSearchResponse;

public interface RouteService {

  RouteSearchResponse searchRoutes(double startX, double startY, double endX, double endY,
      int lang);

  RouteLaneResponse loadRouteLane(String mapObj, int lang);
}
