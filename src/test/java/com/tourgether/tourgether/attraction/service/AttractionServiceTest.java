package com.tourgether.tourgether.attraction.service;

import com.tourgether.tourgether.attraction.dto.AttractionDetailResponse;
import com.tourgether.tourgether.attraction.dto.AttractionResponse;
import com.tourgether.tourgether.attraction.entity.Attraction;
import com.tourgether.tourgether.attraction.entity.AttractionTranslation;
import com.tourgether.tourgether.attraction.entity.LevelDescription;
import com.tourgether.tourgether.attraction.repository.AttractionTranslationRepository;
import com.tourgether.tourgether.attraction.repository.LevelDescriptionRepository;
import com.tourgether.tourgether.attraction.service.impl.AttractionServiceImpl;
import com.tourgether.tourgether.language.entity.Language;
import com.tourgether.tourgether.language.repository.LanguageRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttractionServiceTest {

  @Mock
  private LanguageRepository languageRepository;

  @Mock
  private AttractionTranslationRepository translationRepository;

  @Mock
  private LevelDescriptionRepository levelDescriptionRepository;

  @InjectMocks
  private AttractionServiceImpl attractionService;

  private Language language;
  private Attraction attraction;

  private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

  private Point createPoint(double latitude, double longitude) {
    return geometryFactory.createPoint(new Coordinate(longitude, latitude));
  }

  @BeforeEach
  void setUp() {
    language = new Language(1L, "ko");
    Point location = createPoint(37.0, 127.0);
    attraction = new Attraction(1L, location);
  }

  @Test
  @DisplayName("언어 ID와 키워드로 관광지를 검색할 수 있다")
  void searchAttractionsSuccess() {
    // given
    AttractionTranslation translation = new AttractionTranslation(
        1L,
        language,
        attraction,
        "경복궁",
        "서울 종로구",
        "조선 시대 궁궐",
        null,
        "경복궁은 조선의 궁궐입니다.",
        null,
        null,
        null,
        List.of()
    );

    when(languageRepository.findById(1L)).thenReturn(Optional.of(language));
    when(translationRepository.searchByKeywordInFields(language, "조선"))
        .thenReturn(List.of(translation));

    // when
    List<AttractionResponse> results = attractionService.searchAttractions(1L, "조선");

    // then
    assertThat(results).hasSize(1);
    assertThat(results.get(0).name()).isEqualTo("경복궁");

    verify(languageRepository).findById(1L);
    verify(translationRepository).searchByKeywordInFields(language, "조선");
  }

  @Test
  @DisplayName("위치 기반으로 주변 관광지를 검색할 수 있다")
  void searchNearbyAttractionsSuccess() {
    // given
    Point location = createPoint(37.0, 127.0);

    Attraction nearbyAttraction = new Attraction(1L, location);

    AttractionTranslation nearbyTranslation = new AttractionTranslation(
        1L,
        language,
        nearbyAttraction,
        "경복궁",
        "서울 종로구",
        "조선 시대 궁궐",
        null,
        "경복궁은 조선의 궁궐입니다.",
        null,
        null,
        null,
        List.of()
    );

    when(translationRepository.findNearbyAttractionsByLanguageId(37.0, 127.0, 1000, 1L))
        .thenReturn(List.of(nearbyTranslation));

    // when
    List<AttractionResponse> results = attractionService.searchNearbyAttractions(37.0, 127.0, 1000,
        1L);

    // then
    assertThat(results).hasSize(1);
    assertThat(results.get(0).name()).isEqualTo("경복궁");

    verify(translationRepository).findNearbyAttractionsByLanguageId(37.0, 127.0, 1000, 1L);
  }

  @Test
  @DisplayName("여행지 상세 정보를 언어 ID와 관광지 ID로 조회할 수 있다")
  void getAttractionDetailSuccess() {
    // given
    AttractionTranslation translation = new AttractionTranslation(
        1L,
        language,
        attraction,
        "경복궁",
        "서울 종로구",
        "조선 시대 궁궐",
        null,
        "경복궁은 조선의 궁궐입니다.",
        "화요일",
        "09:00",
        "월요일",
        List.of()
    );

    when(translationRepository.findByAttractionIdAndLanguageId(1L, 1L))
        .thenReturn(Optional.of(translation));

    // when
    AttractionDetailResponse attractionDetail = attractionService.getAttractionDetail(1L, 1L);

    // then
    assertThat(attractionDetail.name()).isEqualTo("경복궁");
    assertThat(attractionDetail.address()).isEqualTo("서울 종로구");
    assertThat(attractionDetail.audioText()).contains("조선의 궁궐");
    verify(translationRepository).findByAttractionIdAndLanguageId(1L, 1L);
  }

  @Test
  @DisplayName("존재하지 않는 언어 ID 또는 관광지 ID일 경우 예외가 발생한다")
  void getAttractionDetailFail() {
    // given
    when(translationRepository.findByAttractionIdAndLanguageId(999L, 1L))
        .thenReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> attractionService.getAttractionDetail(999L, 1L))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("해당 언어의 여행지 정보를 찾을 수 없습니다.");
  }

  @Test
  @DisplayName("여행지 단계별 설명을 관광지 ID와 언어 ID로 조회할 수 있다")
  void getAttractionLevelDescriptionsSuccess() {
    // given
    AttractionTranslation translation = new AttractionTranslation(
        1L,
        language,
        attraction,
        "경복궁",
        "서울 종로구",
        "조선 시대 궁궐",
        null,
        "경복궁은 조선의 궁궐입니다.",
        "화요일",
        "09:00",
        "월요일",
        List.of()
    );

    LevelDescription level1 = new LevelDescription(1L, "입구에서 정전까지", translation);
    LevelDescription level2 = new LevelDescription(2L, "정전 내부 설명", translation);

    when(levelDescriptionRepository.findByTranslationAttractionIdAndTranslationLanguageId(1L, 1L))
        .thenReturn(List.of(level1, level2));

    // when
    var result = attractionService.getAttractionLevelDescriptions(1L, 1L);

    // then
    assertThat(result).hasSize(2);
    assertThat(result.get(0).description()).isEqualTo("입구에서 정전까지");
    assertThat(result.get(1).description()).isEqualTo("정전 내부 설명");
    verify(levelDescriptionRepository).findByTranslationAttractionIdAndTranslationLanguageId(1L,
        1L);
  }
}
