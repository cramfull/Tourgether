package com.tourgether.tourgether.attraction.controller;

import com.tourgether.tourgether.attraction.dto.AttractionDetailResponse;
import com.tourgether.tourgether.attraction.dto.AttractionSummaryResponse;
import com.tourgether.tourgether.attraction.dto.LevelDescriptionResponse;
import com.tourgether.tourgether.attraction.service.AttractionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AttractionController.class)
@AutoConfigureMockMvc(addFilters = false)
class AttractionControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AttractionService attractionService;

  @TestConfiguration
  static class TestConfig {

    @Bean
    @Primary
    public AttractionService attractionService() {
      return Mockito.mock(AttractionService.class);
    }
  }

  @Test
  @DisplayName("GET /api/v1/attractions - 정상 검색 시 200 OK와 ApiResponse 반환")
  void getAttractionsSuccess() throws Exception {
    // given
    AttractionSummaryResponse response = new AttractionSummaryResponse(
        1L,
        "경복궁",
        "서울 종로구",
        "조선 시대 궁궐",
        "url"
    );

    when(attractionService.searchAttractions(1L, "경복궁"))
        .thenReturn(List.of(response));

    // when & then
    mockMvc.perform(get("/api/v1/attractions")
            .param("lang", "1")
            .param("keyword", "경복궁")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.code").value(200))
        .andExpect(jsonPath("$.message").value("요청 성공"))
        .andExpect(jsonPath("$.data[0].name").value("경복궁"));
  }

  @Test
  @DisplayName("GET /api/v1/attractions - keyword 없이 요청 시에도 200 OK")
  void getAttractionsNoKeyword() throws Exception {
    // given
    when(attractionService.searchAttractions(1L, null)).thenReturn(List.of());

    // when & then
    mockMvc.perform(get("/api/v1/attractions")
            .param("lang", "1")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data").isArray());
  }

  @Test
  @DisplayName("GET /api/v1/attractions/nearby - 정상 요청 시 200 OK와 ApiResponse 반환")
  void getNearbyAttractionsSuccess() throws Exception {
    // given
    AttractionSummaryResponse response = new AttractionSummaryResponse(
        1L,
        "경복궁",
        "서울 종로구",
        "조선 시대 궁궐",
        "url"
    );

    when(attractionService.searchNearbyAttractions(37.5796, 126.9770, 1000, 1L))
        .thenReturn(List.of(response));

    // when & then
    mockMvc.perform(get("/api/v1/attractions/nearby")
            .param("latitude", "37.5796")
            .param("longitude", "126.9770")
            .param("radius", "1000")
            .param("languageId", "1")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.code").value(200))
        .andExpect(jsonPath("$.message").value("요청 성공"))
        .andExpect(jsonPath("$.data[0].name").value("경복궁"));
  }

  @Test
  @DisplayName("GET /api/v1/attractions/{id} - 상세 조회 시 200 OK와 ApiResponse 반환")
  void getAttractionDetailSuccess() throws Exception {
    // given
    AttractionDetailResponse detailResponse = new AttractionDetailResponse(
        1L,
        "경복궁",
        "서울 종로구",
        "조선 시대 궁궐",
        "화요일",
        "09:00",
        "월요일",
        "경복궁은 조선의 법궁입니다.",
        "http://audio.com/경복궁.mp3",
        new BigDecimal("37.5796"),
        new BigDecimal("126.9770")
    );

    when(attractionService.getAttractionDetail(1L, 1L)).thenReturn(detailResponse);

    // when & then
    mockMvc.perform(get("/api/v1/attractions/1")
            .param("lang", "1")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.code").value(200))
        .andExpect(jsonPath("$.message").value("요청 성공"))
        .andExpect(jsonPath("$.data.name").value("경복궁"))
        .andExpect(jsonPath("$.data.audioText").value("경복궁은 조선의 법궁입니다."))
        .andExpect(jsonPath("$.data.audioUrl").value("http://audio.com/경복궁.mp3"))
        .andExpect(jsonPath("$.data.openingDay").value("화요일"))
        .andExpect(jsonPath("$.data.openingTime").value("09:00"))
        .andExpect(jsonPath("$.data.closedDay").value("월요일"));
  }

  @Test
  @DisplayName("GET /api/v1/attractions/{translationId}/levels - 단계별 설명 정상 조회")
  void getAttractionLevelDescriptionsByTranslationIdSuccess() throws Exception {
    // given
    LevelDescriptionResponse level1 = new LevelDescriptionResponse(1L, "입구에서 정전까지");
    LevelDescriptionResponse level2 = new LevelDescriptionResponse(2L, "정전 내부 설명");

    when(attractionService.getAttractionLevelDescriptions(1L))
        .thenReturn(List.of(level1, level2));

    // when & then
    mockMvc.perform(get("/api/v1/attractions/1/levels")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.code").value(200))
        .andExpect(jsonPath("$.message").value("요청 성공"))
        .andExpect(jsonPath("$.data[0].description").value("입구에서 정전까지"))
        .andExpect(jsonPath("$.data[1].description").value("정전 내부 설명"));
  }
}
