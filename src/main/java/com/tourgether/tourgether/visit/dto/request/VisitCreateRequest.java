package com.tourgether.tourgether.visit.dto.request;

import jakarta.validation.constraints.NotNull;

public record VisitCreateRequest(
    @NotNull(message = "관관지 ID는 필수입니다.")
    Long attractionId
) {

}
