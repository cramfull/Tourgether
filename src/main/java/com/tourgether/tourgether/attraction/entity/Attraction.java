package com.tourgether.tourgether.attraction.entity;

import com.tourgether.tourgether.attraction.enums.Area;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.locationtech.jts.geom.Point;

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

  @Column(columnDefinition = "geometry(Point, 4326)", nullable = false)
  private Point location;

  @Column(name = "thumbnail_img_url", nullable = false, columnDefinition = "TEXT")
  private String thumbnailImgUrl;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private Area area;

  @OneToMany(mappedBy = "attraction", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<AttractionLike> attractionLikes = new ArrayList<>();
}
