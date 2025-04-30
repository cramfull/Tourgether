package com.tourgether.tourgether.auth.oauth.strategy;

import com.tourgether.tourgether.auth.exception.OAuth2ApiRequestException;
import com.tourgether.tourgether.auth.oauth.dto.NaverUserResponse;
import com.tourgether.tourgether.auth.oauth.dto.OAuth2UserInfo;
import com.tourgether.tourgether.auth.util.OAuth2ClientUtil;
import com.tourgether.tourgether.member.enums.Provider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class NaverOAuth2Strategy implements OAuth2Strategy {

    private final OAuth2ClientUtil oAuth2ClientUtil;
    private final String USERINFO_URI;
    private final String UNLINK_URI;
    private final String CLIENT_ID;
    private final String CLIENT_SECRET;
    private final Provider PROVIDER;

    public NaverOAuth2Strategy(OAuth2ClientUtil oAuth2ClientUtil,
                               @Value("${spring.oauth2.client.provider.naver.userinfo-uri}") String userInfoUri,
                               @Value("${spring.oauth2.client.provider.naver.unlink-uri}") String unlinkUri,
                               @Value("${spring.oauth2.client.provider.naver.client-id}") String clientId,
                               @Value("${spring.oauth2.client.provider.naver.client_secret}") String clientSecret
    ) {
        this.oAuth2ClientUtil = oAuth2ClientUtil;
        this.USERINFO_URI = userInfoUri;
        this.UNLINK_URI = unlinkUri;
        this.CLIENT_ID = clientId;
        this.CLIENT_SECRET = clientSecret;
        PROVIDER = Provider.NAVER;
    }

    @Override
    public OAuth2UserInfo getOAuth2UserInfo(String providerAccessToken) {
        NaverUserResponse naverUserResponse = oAuth2ClientUtil.getUserInfo(
                USERINFO_URI,
                providerAccessToken,
                NaverUserResponse.class);

        return (naverUserResponse == null) ? null
                : naverUserResponse.toUserInfo();
    }

    @Override
    public void unlink(String accessToken) {
        String url = String.format(
                "%s?grant_type=delete&client_id=%s&client_secret=%s&access_token=%s",
                UNLINK_URI, CLIENT_ID, CLIENT_SECRET, accessToken);

        ResponseEntity<String> response = oAuth2ClientUtil.unlink(url, new HttpHeaders());

        if (!isSuccess(response)) {
            throw new OAuth2ApiRequestException(String.format("OAuth2 연동해제에 실패하였습니다. url: %s", UNLINK_URI));
        }
    }

    @Override
    public boolean isSuccess(ResponseEntity<String> response) {
        if (response == null) {
            return false;
        }
        return response.getStatusCode().is2xxSuccessful() && response.getBody().contains("success");
    }

    @Override
    public Provider getProvider() {
        return PROVIDER;
    }
}
