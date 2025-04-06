package com.tourgether.tourgether.visit.dto.response;

import com.tourgether.tourgether.visit.entity.Visit;
import java.time.LocalDateTime;

public record VisitResponse(
    Long visitId,
    Long attractionId,
    LocalDateTime visitedAt
) {

  public static VisitResponse from(Visit visit) {
    return new VisitResponse(
        visit.getId(),
        visit.getAttraction().getId(),
        visit.getVisitedAt()
    );
  }
}
