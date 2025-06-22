package com.tourgether.tourgether.language.service;

import com.tourgether.tourgether.language.dto.LanguageResponse;
import java.util.List;

public interface LanguageService {

  List<LanguageResponse> getAllLanguages();
}
