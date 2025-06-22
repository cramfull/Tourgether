package com.tourgether.tourgether.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LanguageUpdateRequest(
    @NotNull(message = "언어 ID는 필수입니다.")
    Long languageId,

    @NotBlank(message = "언어 설정은 필수입니다.")
    String languageCode
) {

}
