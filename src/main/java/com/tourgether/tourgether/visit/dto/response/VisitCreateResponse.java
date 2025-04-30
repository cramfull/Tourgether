package com.tourgether.tourgether.visit.dto.response;

import com.tourgether.tourgether.visit.entity.Visit;
import java.time.LocalDateTime;

public record VisitCreateResponse(
    Long visitId,
    Long attractionId,
    LocalDateTime visitedAt
) {

  public static VisitCreateResponse from(Visit visit) {
    return new VisitCreateResponse(
        visit.getId(),
        visit.getAttraction().getId(),
        visit.getVisitedAt()
    );
  }
}
