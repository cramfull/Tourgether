package com.tourgether.tourgether.language.controller;

import com.tourgether.tourgether.language.dto.LanguageResponse;
import com.tourgether.tourgether.language.service.LanguageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/languages")
public class LanguageController {

  private final LanguageService languageService;

  @GetMapping
  public ResponseEntity<List<LanguageResponse>> getLanguages() {
    List<LanguageResponse> responses = languageService.getAllLanguages();
    return ResponseEntity.ok(responses);
  }
}
