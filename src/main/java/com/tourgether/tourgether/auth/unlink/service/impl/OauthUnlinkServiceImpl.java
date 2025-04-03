package com.tourgether.tourgether.auth.unlink.service.impl;

import com.tourgether.tourgether.auth.unlink.handler.SocialUnlinkHandler;
import com.tourgether.tourgether.auth.unlink.service.OauthUnlinkService;
import com.tourgether.tourgether.member.enums.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OauthUnlinkServiceImpl implements OauthUnlinkService {

    private final List<SocialUnlinkHandler> unlinkHandlers;

    @Override
    public void unlink(Provider provider, String identifier) {
        unlinkHandlers.stream()
            .filter(h -> h.getProvider().equals(provider))
            .findAny()
            .orElseThrow(() -> new UnsupportedOperationException("지원하지 않는 소셜 로그인입니다."))
            .unlink(identifier);
    }
}
