package com.tourgether.tourgether.attraction.entity;

import com.tourgether.tourgether.member.entity.Member;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "attraction_likes")
public class AttractionLike {

  @EmbeddedId
  private AttractionLikeId id;

  @MapsId("memberId")
  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @MapsId("attractionId")
  @ManyToOne
  @JoinColumn(name = "attraction_id")
  private Attraction attraction;
}
