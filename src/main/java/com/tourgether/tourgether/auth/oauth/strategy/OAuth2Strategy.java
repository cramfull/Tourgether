package com.tourgether.tourgether.auth.oauth.strategy;

import com.tourgether.tourgether.auth.oauth.dto.OAuth2UserInfo;
import com.tourgether.tourgether.member.enums.Provider;
import org.springframework.http.ResponseEntity;

public interface OAuth2Strategy {
    OAuth2UserInfo getOAuth2UserInfo(String providerAccessToken);
    void unlink(String accessToken);
    boolean isSuccess(ResponseEntity<String> response);
    Provider getProvider();
}
