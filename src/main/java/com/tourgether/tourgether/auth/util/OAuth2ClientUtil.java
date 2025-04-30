package com.tourgether.tourgether.auth.util;

import com.tourgether.tourgether.auth.exception.InvalidTokenException;
import com.tourgether.tourgether.auth.exception.OAuth2ApiRequestException;
import com.tourgether.tourgether.auth.oauth.dto.OAuth2UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2ClientUtil {

    private final RestClient restClient;

    public <T extends OAuth2UserResponse> T getUserInfo(String url, String token, Class<T> clazz) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            RestClient.RequestHeadersSpec<?> request = restClient.get()
                    .uri(url);

            headers.forEach((key, values) -> values.forEach(value -> request.header(key, value)));

            return request.retrieve().body(clazz);
        } catch (Exception e) {
            log.info("OAuth2 사용자 정보를 가져오지 못했습니다. url: {}", url);
            throw new OAuth2ApiRequestException(String.format("OAuth2 사용자 정보를 가져오지 못했습니다. url: %s", url));
        }
    }

    public ResponseEntity<String> unlink(String url, HttpHeaders headers) {
        try {
            RestClient.RequestBodySpec request = restClient.post()
                    .uri(url);
            headers.forEach((key, values) -> values.forEach(value -> request.header(key, value)));

            return request.retrieve().toEntity(String.class);
        } catch (Exception e) {
            log.error("소셜 연동 해제 요청 실패: {}", e.getMessage());
            throw new OAuth2ApiRequestException(String.format("OAuth2 연동해제에 실패하였습니다. url: %s", url));
        }
    }
}
