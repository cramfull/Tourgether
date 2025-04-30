package com.tourgether.tourgether.auth.unlink.handler.impl;

import com.tourgether.tourgether.auth.unlink.handler.SocialUnlinkHandler;
import com.tourgether.tourgether.member.enums.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class KakaoUnlinkHandler implements SocialUnlinkHandler {

    private final RestClient restClient;

    //TODO: yaml설정 후 Value 어노테이션 추가 예정
    private String kakaoAdminKey;

    @Override
    public Provider getProvider() {
        return Provider.KAKAO;
    }

    @Override
    public void unlink(String providerId) {
        try {
            ResponseEntity<Void> response = restClient.post()
                .uri("https://kapi.kakao.com/v1/user/unlink")
                .header("Authorization", "KakaoAK " + kakaoAdminKey)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body("target_id_type=user_id&target_id=" + providerId)
                .retrieve()
                .toBodilessEntity();

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("카카오 연동 해제 실패 - 상태 코드: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("카카오 연동 해제 실패", e);
        }
    }
}
