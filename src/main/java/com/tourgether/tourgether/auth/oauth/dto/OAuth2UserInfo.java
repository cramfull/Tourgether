package com.tourgether.tourgether.auth.oauth.dto;


import com.tourgether.tourgether.member.enums.Provider;

public record OAuth2UserInfo (
        String providerId,
        Provider providerType,
        String nickname,
        String profileImage
){
}
