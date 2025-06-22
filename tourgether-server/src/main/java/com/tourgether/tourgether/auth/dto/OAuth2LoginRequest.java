package com.tourgether.tourgether.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record OAuth2LoginRequest (
        @NotBlank(message = "해당 소셜 토큰이 누락되었습니다.")
        String socialAccessToken
){
}
