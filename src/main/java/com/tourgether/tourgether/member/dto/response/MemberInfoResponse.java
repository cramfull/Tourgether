package com.tourgether.tourgether.member.dto.response;

import com.tourgether.tourgether.member.entity.Member;

public record MemberInfoResponse(
    String provider,
    String nickname,
    String profileImage,
    Long languageId,
    String languageCode
) {

  public static MemberInfoResponse from(Member member) {
    return new MemberInfoResponse(
        member.getProvider().name(),
        member.getNickname(),
        member.getProfileImage(),
        member.getLanguageId().getId(),
        member.getLanguageId().getLanguageCode()
    );
  }
}
