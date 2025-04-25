package com.tourgether.tourgether.route.client;

import com.tourgether.tourgether.route.dto.response.RouteLaneResponse;
import com.tourgether.tourgether.route.dto.response.RouteSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ODsayClient {

  private final RestClient restClient;

  @Value("${odsay.api.key}")
  private String apiKey;

  public ODsayClient(RestClient restClient) {
    this.restClient = restClient;
  }

  // 대중교통 경로 검색
  public RouteSearchResponse searchRoute(double startX, double startY, double endX, double endY,
      int lang) {

    return restClient.get()
        .uri(uriBuilder -> uriBuilder
            .scheme("https")
            .host("api.odsay.com")
            .path("/v1/api/searchPubTransPathT")
            .queryParam("apiKey", apiKey)
            .queryParam("SX", startX)
            .queryParam("SY", startY)
            .queryParam("EX", endX)
            .queryParam("EY", endY)
            .queryParam("lang", lang)
            .queryParam("OPT", 0)
            .build())
        .retrieve()
        .body(RouteSearchResponse.class);
  }

  // 경로 폴리라인 데이터 요청 (loadLane)
  public RouteLaneResponse loadLane(String mapObj, int lang) {
    String finalMapObj = mapObj.startsWith("0:0@") ? mapObj : "0:0@" + mapObj;

    return restClient.get()
        .uri(uriBuilder -> uriBuilder
            .scheme("https")
            .host("api.odsay.com")
            .path("/v1/api/loadLane")
            .queryParam("apiKey", apiKey)
            .queryParam("mapObject", finalMapObj)
            .queryParam("lang", lang)
            .build())
        .retrieve()
        .body(RouteLaneResponse.class);
  }
}
