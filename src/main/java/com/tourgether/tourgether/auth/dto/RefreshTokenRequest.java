package com.tourgether.tourgether.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank(message = "RefreshToken 이 누락되었습니다.")
        String refreshToken
) {
}
