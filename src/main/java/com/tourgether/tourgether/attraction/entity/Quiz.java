package com.tourgether.tourgether.attraction.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "quizzes")
public class Quiz {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "question", nullable = false, columnDefinition = "TEXT")
  private String question;

  @Column(name = "answer", nullable = false)
  private Boolean answer;

  @ManyToOne
  @JoinColumn(name = "translation_id", insertable = false, updatable = false)
  private AttractionTranslation translation;
}
