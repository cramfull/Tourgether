package com.tourgether.tourgether.attraction.repository;

import com.tourgether.tourgether.attraction.entity.Attraction;
import com.tourgether.tourgether.attraction.entity.AttractionTranslation;
import com.tourgether.tourgether.language.entity.Language;
import com.tourgether.tourgether.language.repository.LanguageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class AttractionTranslationRepositoryTest {

  @Autowired
  private AttractionRepository attractionRepository;

  @Autowired
  private AttractionTranslationRepository translationRepository;

  @Autowired
  private LanguageRepository languageRepository;

  @Test
  @DisplayName("이름, 요약, 오디오 텍스트 중 하나라도 키워드를 포함하면 검색된다")
  void searchByKeywordInFieldsTest() {
    Language language = new Language(null, "ko");
    Language savedLanguage = languageRepository.save(language);

    Attraction attraction = new Attraction(null, new BigDecimal("37.0"), new BigDecimal("127.0"));
    Attraction attraction2 = new Attraction(null, new BigDecimal("37.0"), new BigDecimal("127.0"));
    Attraction savedAttraction = attractionRepository.save(attraction);
    Attraction savedAttraction2 = attractionRepository.save(attraction2);

    AttractionTranslation translation = new AttractionTranslation(
        null,
        savedLanguage,
        savedAttraction,
        "광화문",
        "서울 종로구 효자로 12 국립고궁박물관",
        "아름다운 건축 양식으로 유명합니다",
        null,
        "조선 시대의 상징적인 문입니다",
        null, null, null,
        List.of()
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
        null, null, null,
        List.of()
    );

    translationRepository.save(translation);
    translationRepository.save(translation2);

    List<AttractionTranslation> results =
        translationRepository.searchByKeywordInFields(savedLanguage, "조선");

    assertThat(results).hasSize(2);
    List<String> names = results.stream().map(AttractionTranslation::getName).toList();
    assertThat(names).containsExactlyInAnyOrder("광화문", "조선");
  }

}

