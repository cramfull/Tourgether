package com.tourgether.tourgether.auth.unlink.handler.impl;

import com.tourgether.tourgether.auth.unlink.handler.SocialUnlinkHandler;
import com.tourgether.tourgether.member.enums.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class NaverUnlinkHandler implements SocialUnlinkHandler {

    private final RestClient restClient;

    //TODO: yaml설정 후 Value 어노테이션 추가 예정
    private String clientId;

    //TODO: yaml설정 후 Value 어노테이션 추가 예정
    private String clientSecret;

    @Override
    public Provider getProvider() {
        return Provider.NAVER;
    }

    @Override
    public void unlink(String accessToken) {
        String url = String.format(
            "https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=%s&client_secret=%s&access_token=%s",
            clientId, clientSecret, accessToken
        );

        try {
            ResponseEntity<Void> response = restClient.post()
                .uri(url)
                .retrieve()
                .toBodilessEntity();

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("네이버 연동 해제 실패 - 상태 코드: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("네이버 연동 해제 실패", e);
        }
    }
}
