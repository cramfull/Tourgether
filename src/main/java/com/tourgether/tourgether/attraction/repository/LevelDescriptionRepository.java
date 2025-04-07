package com.tourgether.tourgether.attraction.repository;

import com.tourgether.tourgether.attraction.entity.LevelDescription;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelDescriptionRepository extends JpaRepository<LevelDescription, Long> {

  List<LevelDescription> findByTranslationAttractionIdAndTranslationLanguageId(
      Long attractionId,
      Long languageId
  );
}
