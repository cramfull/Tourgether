package com.tourgether.tourgether.member.service;

import com.tourgether.tourgether.auth.CustomUserDetails;
import com.tourgether.tourgether.member.dto.response.MemberInfoResponse;
import com.tourgether.tourgether.member.dto.response.NicknameUpdateResponse;
import com.tourgether.tourgether.member.entity.Member;

public interface MemberService {

  void withdraw(Long memberId, String socialAccessToken);

  void updateLanguage(Long memberId, String languageCode);

  NicknameUpdateResponse updateNickname(Long memberId, String nickname);

  MemberInfoResponse getMemberInfo(Long memberId);
}
