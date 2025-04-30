package com.tourgether.tourgether.auth.unlink.service;

import com.tourgether.tourgether.member.enums.Provider;

public interface OauthUnlinkService {

    void unlink(Provider provider, String identifier);
}
