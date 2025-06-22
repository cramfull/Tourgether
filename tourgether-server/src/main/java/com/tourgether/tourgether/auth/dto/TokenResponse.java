package com.tourgether.tourgether.auth.dto;

public record TokenResponse(
    String accessToken,
    String refreshToken
) {
}
