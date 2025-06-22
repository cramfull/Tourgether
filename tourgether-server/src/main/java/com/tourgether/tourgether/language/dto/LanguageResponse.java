package com.tourgether.tourgether.language.dto;

import com.tourgether.tourgether.language.entity.Language;

public record LanguageResponse(
    Long id,
    String languageCode) {

  public static LanguageResponse from(Language language) {
    return new LanguageResponse(language.getId(), language.getLanguageCode());
  }
}
