package com.tourgether.tourgether.auth.util;

import com.tourgether.tourgether.auth.exception.InvalidTokenException;
import com.tourgether.tourgether.auth.oauth.dto.OAuth2UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2ClientUtil {

    private final RestClient restClient;
    private final HeaderUtil headerUtil;

    public <T extends OAuth2UserResponse> T getUserInfo(String url, String token, Class<T> clazz) {
        try {
            return restClient.get()
                    .uri(url)
                    .header(headerUtil.getPREFIX_AUTHORIZATION(),
                            headerUtil.getPREFIX_TOKEN_BEARER() + token)
                    .retrieve()
                    .body(clazz);
        } catch (Exception e) {
            log.info("OAuth2 사용자 정보를 가져오지 못했습니다. url: {}", url);
            throw new InvalidTokenException(String.format("OAuth2 사용자 정보를 가져오지 못했습니다. url: %s", url));
        }
    }
}
