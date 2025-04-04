package com.tourgether.tourgether.member.service;

import com.tourgether.tourgether.auth.CustomUserDetails;
import com.tourgether.tourgether.member.dto.response.NicknameUpdateResponse;

public interface MemberService {

    void withdraw(CustomUserDetails userDetails);

    void updateLanguage(Long memberId, String languageCode);

    NicknameUpdateResponse updateNickname(Long memberId, String nickname);
}