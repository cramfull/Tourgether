package com.tourgether.tourgether.member.enums;

import lombok.Getter;
import java.util.Arrays;

@Getter
public enum Provider {
    KAKAO("kakao"),
    NAVER("naver"),
    GOOGLE("google");

    private final String providerType;

    Provider(String providerType) {
        this.providerType = providerType;
    }

    public static Provider from(String providerType) {
        return Arrays.stream(values())
                .filter(p -> p.getProviderType().equalsIgnoreCase(providerType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 provider 입니다: " + providerType));
    }
}
