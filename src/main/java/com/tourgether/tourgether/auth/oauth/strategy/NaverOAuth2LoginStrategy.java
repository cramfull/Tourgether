package com.tourgether.tourgether.auth.oauth.strategy;

import com.tourgether.tourgether.auth.oauth.dto.NaverUserResponse;
import com.tourgether.tourgether.auth.oauth.dto.OAuth2UserInfo;
import com.tourgether.tourgether.auth.util.OAuth2ClientUtil;
import com.tourgether.tourgether.member.enums.Provider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NaverOAuth2LoginStrategy implements OAuth2LoginStrategy {

    private final OAuth2ClientUtil oAuth2ClientUtil;
    private final String PROVIDER_URL;
    private final Provider PROVIDER;

    public NaverOAuth2LoginStrategy(OAuth2ClientUtil oAuth2ClientUtil,
                                    @Value("${spring.oauth2.client.provider.naver.url}") String provider_url
    ) {
        this.oAuth2ClientUtil = oAuth2ClientUtil;
        this.PROVIDER_URL = provider_url;
        this.PROVIDER = Provider.NAVER;
    }

    @Override
    public OAuth2UserInfo getOAuth2UserInfo(String providerAccessToken) {
        NaverUserResponse naverUserResponse = oAuth2ClientUtil.getUserInfo(
                PROVIDER_URL,
                providerAccessToken,
                NaverUserResponse.class);

        return (naverUserResponse == null) ? null
                : naverUserResponse.toUserInfo();
    }

    @Override
    public Provider getProvider() {
        return PROVIDER;
    }
}
