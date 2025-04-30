package com.tourgether.tourgether.auth.oauth.dto;

import com.tourgether.tourgether.member.enums.Provider;
import jakarta.validation.constraints.NotBlank;

public record GoogleUserResponse(
        @NotBlank
        String sub,
        @NotBlank
        String name,
        @NotBlank
        String picture
) implements OAuth2UserResponse {
    @Override
    public OAuth2UserInfo toUserInfo() {
        return new OAuth2UserInfo(
                sub(),
                Provider.GOOGLE,
                name(),
                picture);
    }
}
