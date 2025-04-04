package com.tourgether.tourgether.attraction.repository;

import com.tourgether.tourgether.attraction.entity.AttractionTranslation;
import com.tourgether.tourgether.language.entity.Language;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AttractionTranslationRepository extends
    JpaRepository<AttractionTranslation, Long> {

  @Query("""
          SELECT a FROM AttractionTranslation a
          WHERE a.language = :language
            AND (LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
              OR LOWER(a.summary) LIKE LOWER(CONCAT('%', :keyword, '%'))
              OR LOWER(a.audioText) LIKE LOWER(CONCAT('%', :keyword, '%')))
      """)
  List<AttractionTranslation> searchByKeywordInFields(
      @Param("language") Language language,
      @Param("keyword") String keyword
  );
}
