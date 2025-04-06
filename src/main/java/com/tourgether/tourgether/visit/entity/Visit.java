package com.tourgether.tourgether.visit.entity;

import com.tourgether.tourgether.attraction.entity.Attraction;
import com.tourgether.tourgether.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "visits")
public class Visit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @ManyToOne
  @JoinColumn(name = "attraction_id", nullable = false)
  private Attraction attraction;

  @Column(name = "visited_at", nullable = false)
  private LocalDateTime visitedAt;

  public static Visit of(Member member, Attraction attraction) {
    return new Visit(null, member, attraction, LocalDateTime.now());
  }
}
