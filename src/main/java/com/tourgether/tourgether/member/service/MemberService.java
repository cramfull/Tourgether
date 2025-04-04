package com.tourgether.tourgether.member.service;

import com.tourgether.tourgether.auth.CustomUserDetails;

public interface MemberService {

    void withdraw(CustomUserDetails userDetails);

    void updateLanguage(Long memberId, String languageCode);
}
