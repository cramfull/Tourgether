package com.tourgether.tourgether.auth.oauth.strategy;

import com.tourgether.tourgether.auth.exception.OAuth2ApiRequestException;
import com.tourgether.tourgether.auth.oauth.dto.GoogleUserResponse;
import com.tourgether.tourgether.auth.oauth.dto.OAuth2UserInfo;
import com.tourgether.tourgether.auth.util.OAuth2ClientUtil;
import com.tourgether.tourgether.member.enums.Provider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class GoogleOAuth2Strategy implements OAuth2Strategy {

    private final OAuth2ClientUtil oAuth2ClientUtil;
    private final String USERINFO_URI;
    private final String UNLINK_URI;
    private final Provider PROVIDER;

    public GoogleOAuth2Strategy(OAuth2ClientUtil oAuth2ClientUtil,
                                @Value("${spring.oauth2.client.provider.kakao.userinfo-uri}") String userInfoUri,
                                @Value("${spring.oauth2.client.provider.kakao.unlink-uri}") String unlinkUri
    ) {
        this.oAuth2ClientUtil = oAuth2ClientUtil;
        this.USERINFO_URI = userInfoUri;
        this.UNLINK_URI = unlinkUri;
        PROVIDER = Provider.GOOGLE;
    }

    @Override
    public OAuth2UserInfo getOAuth2UserInfo(String providerAccessToken) {
        //TODO 개발용 엔드포인트임.
        // 서비스시, https://www.googleapis.com/oauth2/v3/certs 로
        // 구글 공개키 캐싱하여 직접 디코딩 후 검증 필요.
        GoogleUserResponse googleUserResponse = oAuth2ClientUtil.getUserInfo(
                USERINFO_URI + providerAccessToken,
                null,
                GoogleUserResponse.class);

        return (googleUserResponse == null) ? null
                : googleUserResponse.toUserInfo();
    }

    @Override
    public void unlink(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        ResponseEntity<String> response = oAuth2ClientUtil.unlink(String.format("%s?token=%s", UNLINK_URI, accessToken), headers);

        if (!isSuccess(response)) {
            throw new OAuth2ApiRequestException(String.format("OAuth2 연동해제에 실패하였습니다. url: %s", UNLINK_URI));
        }
    }

    @Override
    public boolean isSuccess(ResponseEntity<String> response) {
        if (response == null) {
            return false;
        }
        return response.getStatusCode().is2xxSuccessful();
    }

    @Override
    public Provider getProvider() {
        return this.PROVIDER;
    }
}
