package com.tourgether.tourgether.auth.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tourgether.tourgether.member.enums.Provider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record KakaoUserResponse(
        @NotNull
        Long id,
        @NotNull
        KakaoAccount kakaoAccount
) implements OAuth2UserResponse {
    public record KakaoAccount(
            @NotNull
            Profile profile
    ) {
        public record Profile(
                @NotBlank
                String nickname,
                @NotBlank
                @JsonProperty("profile_image_url")
                String profileImageUrl
        ) {
        }
    }

    @Override
    public OAuth2UserInfo toUserInfo() {
        return new OAuth2UserInfo(
                String.valueOf(id()),
                Provider.KAKAO,
                kakaoAccount().profile().nickname(),
                kakaoAccount().profile().profileImageUrl());
    }
}
