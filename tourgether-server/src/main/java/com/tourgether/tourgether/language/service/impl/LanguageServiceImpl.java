package com.tourgether.tourgether.language.service.impl;

import com.tourgether.tourgether.language.dto.LanguageResponse;
import com.tourgether.tourgether.language.entity.Language;
import com.tourgether.tourgether.language.repository.LanguageRepository;
import com.tourgether.tourgether.language.service.LanguageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

  private final LanguageRepository languageRepository;

  @Override
  public List<LanguageResponse> getAllLanguages() {
    List<Language> languages = languageRepository.findAll();

    return languages.stream()
        .map(LanguageResponse::from)
        .toList();
  }
}
