package com.tourgether.tourgether.attraction.entity;

import com.tourgether.tourgether.language.entity.Language;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
    name = "attraction_translations",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"language_id", "attraction_id"})}
)
public class AttractionTranslation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long translationId;

  @ManyToOne
  @JoinColumn(name = "language_id", nullable = false)
  private Language language;

  @ManyToOne
  @JoinColumn(name = "attraction_id")
  private Attraction attraction;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String address;

  @Column(name = "summary", nullable = false)
  private String summary;

  @Column(name = "audio_url")
  private String audioUrl;

  @Column(name = "audio_text", nullable = false, columnDefinition = "TEXT")
  private String audioText;

  @Column(name = "opening_day")
  private String openingDay;

  @Column(name = "opening_time")
  private String openingTime;

  @Column(name = "closed_day")
  private String closedDay;

}
