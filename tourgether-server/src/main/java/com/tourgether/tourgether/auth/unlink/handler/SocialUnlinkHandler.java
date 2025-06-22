package com.tourgether.tourgether.auth.unlink.handler;

import com.tourgether.tourgether.member.enums.Provider;

public interface SocialUnlinkHandler {

    Provider getProvider();

    // 카카오 - userId, 네이버/구글 - accessToken
    void unlink(String identifier);
}
