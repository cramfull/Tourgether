package com.tourgether.tourgether.attraction.service;

import com.tourgether.tourgether.attraction.dto.AttractionResponse;

import java.util.List;

public interface AttractionService {

  List<AttractionResponse> searchAttractions(Long languageId, String keyword);

}
