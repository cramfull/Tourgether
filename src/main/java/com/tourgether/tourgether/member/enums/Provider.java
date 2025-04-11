package com.tourgether.tourgether.member.enums;

import lombok.Getter;

@Getter
public enum Provider {
    KAKAO("kakao"),
    NAVER("naver"),
    GOOGLE("google");

    private final String providerType;

    Provider(String providerType) {
        this.providerType = providerType;
    }
}
