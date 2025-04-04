package com.tourgether.tourgether.attraction.service;

import com.tourgether.tourgether.attraction.dto.AttractionResponse;
import com.tourgether.tourgether.attraction.entity.Attraction;
import com.tourgether.tourgether.attraction.entity.AttractionTranslation;
import com.tourgether.tourgether.attraction.repository.AttractionTranslationRepository;
import com.tourgether.tourgether.attraction.service.impl.AttractionServiceImpl;
import com.tourgether.tourgether.language.entity.Language;
import com.tourgether.tourgether.language.repository.LanguageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class AttractionServiceTest {

  @Mock
  private LanguageRepository languageRepository;

  @Mock
  private AttractionTranslationRepository translationRepository;

  @InjectMocks
  private AttractionServiceImpl attractionService;

  private Language language;
  private Attraction attraction;

  @BeforeEach
  void setUp() {
    language = new Language(1L, "ko");
    attraction = new Attraction(1L, new BigDecimal("37.0"), new BigDecimal("127.0"));
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
}
