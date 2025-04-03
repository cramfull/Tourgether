package com.tourgether.tourgether.auth.unlink.handler.impl;

import com.tourgether.tourgether.auth.unlink.handler.SocialUnlinkHandler;
import com.tourgether.tourgether.member.enums.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class GoogleUnlinkHandler implements SocialUnlinkHandler {

    private final RestClient restClient;

    @Override
    public Provider getProvider() {
        return Provider.GOOGLE;
    }

    @Override
    public void unlink(String accessToken) {
        // TODO: 추후 url yaml파일에 설정 고려
        String url = "https://oauth2.googleapis.com/revoke?token=" + accessToken;

        try {
            ResponseEntity<Void> response = restClient.post()
                .uri(url)
                .retrieve()
                .toBodilessEntity(); // 바디는 필요 없으니까 Void

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("구글 연동 해제 실패 - 상태 코드: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("구글 연동 해제 중 예외 발생: " + e.getMessage(), e);
        }
    }
}
