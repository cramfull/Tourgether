package com.tourgether.tourgether.auth.oauth.strategy;

import com.tourgether.tourgether.auth.oauth.dto.GoogleUserResponse;
import com.tourgether.tourgether.auth.oauth.dto.OAuth2UserInfo;
import com.tourgether.tourgether.auth.util.OAuth2ClientUtil;
import com.tourgether.tourgether.member.enums.Provider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GoogleOAuth2LoginStrategy implements OAuth2LoginStrategy {

    private final OAuth2ClientUtil oAuth2ClientUtil;
    private final String PROVIDER_URL;
    private final Provider PROVIDER;

    public GoogleOAuth2LoginStrategy(OAuth2ClientUtil oAuth2ClientUtil,
                                     @Value("${spring.oauth2.client.provider.google.url}") String provider_url
    ) {
        this.oAuth2ClientUtil = oAuth2ClientUtil;
        this.PROVIDER_URL = provider_url;
        this.PROVIDER = Provider.GOOGLE;
    }

    @Override
    public OAuth2UserInfo getOAuth2UserInfo(String providerAccessToken) {
        //TODO 개발용 엔드포인트임.
        // 서비스시, https://www.googleapis.com/oauth2/v3/certs 로
        // 구글 공개키 캐싱하여 직접 디코딩 후 검증 필요.
        GoogleUserResponse googleUserResponse = oAuth2ClientUtil.getUserInfo(
                PROVIDER_URL + providerAccessToken,
                null,
                GoogleUserResponse.class);

        return (googleUserResponse == null) ? null
                : googleUserResponse.toUserInfo();
    }

    @Override
    public Provider getProvider() {
        return this.PROVIDER;
    }
}
