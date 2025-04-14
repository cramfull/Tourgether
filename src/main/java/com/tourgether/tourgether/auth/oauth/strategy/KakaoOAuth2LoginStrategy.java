package com.tourgether.tourgether.auth.oauth.strategy;

import com.tourgether.tourgether.auth.oauth.dto.KakaoUserResponse;
import com.tourgether.tourgether.auth.oauth.dto.OAuth2UserInfo;
import com.tourgether.tourgether.auth.util.OAuth2ClientUtil;
import com.tourgether.tourgether.member.enums.Provider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KakaoOAuth2LoginStrategy implements OAuth2LoginStrategy {

    private final OAuth2ClientUtil oAuth2ClientUtil;
    private final String PROVIDER_URL;
    private final Provider PROVIDER;

    public KakaoOAuth2LoginStrategy(OAuth2ClientUtil oAuth2ClientUtil,
                                    @Value("${spring.oauth2.client.provider.kakao.url}") String provider_url
    ) {
        this.oAuth2ClientUtil = oAuth2ClientUtil;
        this.PROVIDER_URL = provider_url;
        PROVIDER = Provider.KAKAO;
    }

    @Override
    public OAuth2UserInfo getOAuth2UserInfo(String providerAccessToken) {
        KakaoUserResponse kakaoUserResponse = oAuth2ClientUtil.getUserInfo(
                PROVIDER_URL,
                providerAccessToken,
                KakaoUserResponse.class);

        return (kakaoUserResponse == null) ? null
                : kakaoUserResponse.toUserInfo();
    }

    @Override
    public Provider getProvider() {
        return PROVIDER;
    }
}
