package com.tourgether.tourgether.auth.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tourgether.tourgether.member.enums.Provider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NaverUserResponse(
        @NotNull
        Response response
) implements OAuth2UserResponse {
    public record Response(
            @NotBlank
            String id,
            @NotBlank
            String nickname,
            @NotBlank
            @JsonProperty("profile_image")
            String profileImage
    ) {
    }

    @Override
    public OAuth2UserInfo toUserInfo() {
        return new OAuth2UserInfo(
                response().id(),
                Provider.NAVER,
                response().nickname(),
                response().profileImage());
    }

}
