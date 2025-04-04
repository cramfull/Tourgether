package com.tourgether.tourgether.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LanguageUpdateRequest(
    @NotBlank(message = "언어 설정은 필수입니다.")
    String languageCode
) {

}
