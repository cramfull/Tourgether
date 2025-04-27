package com.tourgether.tourgether.attraction.repository;

import com.tourgether.tourgether.attraction.entity.AttractionTranslation;
import com.tourgether.tourgether.language.entity.Language;
import java.util.List;
import java.util.Optional;
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

  @Query(value = """
      SELECT at.*
      FROM attraction_translations at
      JOIN attractions a ON at.attraction_id = a.id
      WHERE at.language_id = :languageId
        AND ST_DWithin(
          a.location,
          ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography,
          :radius
        )
      """, nativeQuery = true)
  List<AttractionTranslation> findNearbyAttractionsByLanguageId(
      @Param("lat") double latitude,
      @Param("lon") double longitude,
      @Param("radius") double radiusInMeters,
      @Param("languageId") Long languageId
  );

  Optional<AttractionTranslation> findByTranslationId(Long translationId);

  @Query(value = """
      SELECT at.*
      FROM attraction_translations at
      JOIN (
          SELECT v.attraction_id
          FROM visits v
          GROUP BY v.attraction_id
          ORDER BY COUNT(*) DESC
          LIMIT :limit
      ) popular ON at.attraction_id = popular.attraction_id
      WHERE at.language_id = :languageId
      """, nativeQuery = true)
  List<AttractionTranslation> findTopVisitedAttractions(
      @Param("languageId") Long languageId,
      @Param("limit") int limit
  );

  Optional<AttractionTranslation> findByAttractionIdAndLanguageId(Long attractionId,
      Long languageId);

  @Query("""
          SELECT DISTINCT at
          FROM AttractionTranslation at
          JOIN at.attraction a
          WHERE at.language.id = :languageId
            AND within(
              a.location,
              ST_MakeEnvelope(:swLng, :swLat, :neLng, :neLat, 4326)
            ) = true
      """)
  List<AttractionTranslation> findByTranslationIdMapBounds(
      @Param("swLat") double swLat,
      @Param("swLng") double swLng,
      @Param("neLat") double neLat,
      @Param("neLng") double neLng,
      @Param("languageId") Long languageId
  );
}
