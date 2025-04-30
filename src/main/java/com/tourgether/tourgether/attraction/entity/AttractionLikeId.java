package com.tourgether.tourgether.attraction.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AttractionLikeId implements Serializable {

  @Column(name = "member_id")
  private Long memberId;

  @Column(name = "attraction_id")
  private Long attractionId;
}
