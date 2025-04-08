package com.tourgether.tourgether.attraction.repository;

import com.tourgether.tourgether.attraction.entity.Attraction;
import com.tourgether.tourgether.attraction.entity.AttractionTranslation;
import com.tourgether.tourgether.language.entity.Language;
import com.tourgether.tourgether.language.repository.LanguageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class AttractionTranslationRepositoryTest {

  @Autowired
  private AttractionRepository attractionRepository;

  @Autowired
  private AttractionTranslationRepository translationRepository;

  @Autowired
  private LanguageRepository languageRepository;

  private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

  private Point createPoint(double latitude, double longitude) {
    return geometryFactory.createPoint(new Coordinate(longitude, latitude));
  }

  @Test
  @DisplayName("이름, 요약, 오디오 텍스트 중 하나라도 키워드를 포함하면 검색된다")
  void searchByKeywordInFieldsTest() {
    // given
    Language language = new Language(null, "ko");
    Language savedLanguage = languageRepository.save(language);

    Point location1 = createPoint(37.0, 127.0);
    Point location2 = createPoint(37.0, 127.0);

    Attraction attraction = new Attraction(null, location1, "url");
    Attraction attraction2 = new Attraction(null, location2, "url");
    Attraction savedAttraction = attractionRepository.save(attraction);
    Attraction savedAttraction2 = attractionRepository.save(attraction2);

    AttractionTranslation translation1 = new AttractionTranslation(
        null,
        savedLanguage,
        savedAttraction,
        "광화문",
        "서울 종로구 효자로 12 국립고궁박물관",
        "아름다운 건축 양식으로 유명합니다",
        null,
        "조선 시대의 상징적인 문입니다",
        null, null, null
    );

    AttractionTranslation translation2 = new AttractionTranslation(
        null,
        savedLanguage,
        savedAttraction2,
        "조선",
        "서울 종로구 효자로 12 국립고궁박물관",
        "아름다운 건축 양식으로 유명합니다",
        null,
        "조선 시대의 상징적인 문입니다",
        null, null, null
    );

    translationRepository.save(translation1);
    translationRepository.save(translation2);

    // when
    List<AttractionTranslation> results =
        translationRepository.searchByKeywordInFields(savedLanguage, "조선");

    // then
    assertThat(results).hasSize(2);
    List<String> names = results.stream().map(AttractionTranslation::getName).toList();
    assertThat(names).containsExactlyInAnyOrder("광화문", "조선");
  }

  @Test
  @DisplayName("주어진 좌표 반경 내에 존재하는 관광지를 언어 기준으로 조회한다")
  void findNearbyAttractionsByLanguageIdTest() {
    // given
    Language language = new Language(null, "ko");
    Language savedLanguage = languageRepository.save(language);

    Point location1 = createPoint(37.5796, 126.9770); // 경복궁
    Point location2 = createPoint(37.5700, 126.9830); // 창덕궁
    Point farLocation = createPoint(37.0, 127.0);     // 멀리 떨어진 곳

    Attraction attraction1 = attractionRepository.save(new Attraction(null, location1, "url"));
    Attraction attraction2 = attractionRepository.save(new Attraction(null, location2, "url"));
    Attraction attractionFar = attractionRepository.save(new Attraction(null, farLocation, "url"));

    translationRepository.save(new AttractionTranslation(
        null, savedLanguage, attraction1, "경복궁", "서울 종로구 세종로", "조선 시대의 대표 궁궐",
        null, "경복궁은 조선의 법궁으로...", "화요일~일요일", "09:00~18:00", "월요일"
    ));

    translationRepository.save(new AttractionTranslation(
        null, savedLanguage, attraction2, "창덕궁", "서울 종로구 율곡로", "유네스코 세계유산",
        null, "창덕궁은 자연과 조화를 이룬 궁궐로…", "화요일~일요일", "09:00~17:30", "월요일"
    ));

    translationRepository.save(new AttractionTranslation(
        null, savedLanguage, attractionFar, "멀리있는궁궐", "서울", "먼 곳",
        null, "멀리 떨어진 궁궐입니다", "수요일", "10:00", "화요일"
    ));

    double centerLat = 37.5750;
    double centerLon = 126.9800;
    double radiusMeters = 1000; // 1.0km

    // when
    List<AttractionTranslation> results =
        translationRepository.findNearbyAttractionsByLanguageId(
            centerLat, centerLon, radiusMeters, savedLanguage.getId()
        );

    // then
    assertThat(results).hasSize(2);
    List<String> names = results.stream().map(AttractionTranslation::getName).toList();
    assertThat(names).containsExactlyInAnyOrder("경복궁", "창덕궁");
  }

}
