package com.tourgether.tourgether.attraction.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "attractions")
public class Attraction {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "latitude", precision = 9, scale = 6, nullable = false)
  private BigDecimal latitude;

  @Column(name = "longitude", precision = 9, scale = 6, nullable = false)
  private BigDecimal longitude;
}
