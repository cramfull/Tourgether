package com.tourgether.tourgether.auth.oauth.strategy;

import com.tourgether.tourgether.auth.oauth.dto.OAuth2UserInfo;
import com.tourgether.tourgether.member.enums.Provider;

public interface OAuth2LoginStrategy {
    OAuth2UserInfo getOAuth2UserInfo(String providerAccessToken);
    Provider getProvider();
}
