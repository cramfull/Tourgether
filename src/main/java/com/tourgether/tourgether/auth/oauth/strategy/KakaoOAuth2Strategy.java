package com.tourgether.tourgether.auth.oauth.strategy;

import com.tourgether.tourgether.auth.exception.OAuth2ApiRequestException;
import com.tourgether.tourgether.auth.oauth.dto.KakaoUserResponse;
import com.tourgether.tourgether.auth.oauth.dto.OAuth2UserInfo;
import com.tourgether.tourgether.auth.util.OAuth2ClientUtil;
import com.tourgether.tourgether.member.enums.Provider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class KakaoOAuth2Strategy implements OAuth2Strategy {

    private final OAuth2ClientUtil oAuth2ClientUtil;
    private final String USERINFO_URI;
    private final String UNLINK_URI;
    private final Provider PROVIDER;

    public KakaoOAuth2Strategy(OAuth2ClientUtil oAuth2ClientUtil,
                               @Value("${spring.oauth2.client.provider.kakao.userinfo-uri}") String userInfoUri,
                               @Value("${spring.oauth2.client.provider.kakao.unlink-uri}") String unlinkUri
    ) {
        this.oAuth2ClientUtil = oAuth2ClientUtil;
        this.USERINFO_URI = userInfoUri;
        this.UNLINK_URI = unlinkUri;
        PROVIDER = Provider.KAKAO;
    }

    @Override
    public OAuth2UserInfo getOAuth2UserInfo(String providerAccessToken) {
        KakaoUserResponse kakaoUserResponse = oAuth2ClientUtil.getUserInfo(
                USERINFO_URI,
                providerAccessToken,
                KakaoUserResponse.class);

        return (kakaoUserResponse == null) ? null
                : kakaoUserResponse.toUserInfo();
    }

    @Override
    public void unlink(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(accessToken);

        ResponseEntity<String> response = oAuth2ClientUtil.unlink(UNLINK_URI, headers);
        if (!isSuccess(response)) {
            throw new OAuth2ApiRequestException(String.format("OAuth2 연동해제에 실패하였습니다. url: %s", UNLINK_URI));
        }
    }

    @Override
    public boolean isSuccess(ResponseEntity<String> response) {
        if (response == null) {
            return false;
        }
        return response.getStatusCode().is2xxSuccessful() && response.getBody().contains("id");
    }

    @Override
    public Provider getProvider() {
        return PROVIDER;
    }
}
