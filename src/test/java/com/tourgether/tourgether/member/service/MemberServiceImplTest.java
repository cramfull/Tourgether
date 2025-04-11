package com.tourgether.tourgether.member.service;

import com.tourgether.tourgether.auth.CustomUserDetails;
import com.tourgether.tourgether.auth.unlink.service.OauthUnlinkService;
import com.tourgether.tourgether.language.entity.Language;
import com.tourgether.tourgether.member.entity.Member;
import com.tourgether.tourgether.member.enums.Provider;
import com.tourgether.tourgether.member.enums.Status;
import com.tourgether.tourgether.member.repository.MemberRepository;
import com.tourgether.tourgether.member.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

  @InjectMocks
  private MemberServiceImpl memberService;

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private OauthUnlinkService oauthUnlinkService;

  @Test
  @DisplayName("회원 탈퇴 성공 - 카카오")
  void withdrawKakao() {
    Long memberId = 1L;
    Language language = new Language(1L, "EN");
    Member member = new Member(1L, Provider.KAKAO, "kakao", "nickname", "profile",
        LocalDateTime.now(), LocalDateTime.now(), Status.ACTIVE, language, null);

    CustomUserDetails userDetails = new CustomUserDetails(
        memberId, "kakao", Provider.KAKAO, Status.ACTIVE
    );

    given(memberRepository.getActiveMemberOrThrow(userDetails.memberId())).willReturn(member);

    memberService.withdraw(userDetails);

    assertThat(member.getStatus()).isEqualTo(Status.WITHDRAW);

    verify(oauthUnlinkService, times(1)).unlink(Provider.KAKAO, "kakao");
  }

  @Test
  @DisplayName("네이버 회원 탈퇴 성공")
  void withdrawNaver() {
    Long memberId = 2L;
    Language language = new Language(2L, "KO");
    Member member = new Member(memberId, Provider.NAVER, null, "nickname", "profile",
        LocalDateTime.now(), LocalDateTime.now(), Status.ACTIVE, language, null);

    CustomUserDetails userDetails = new CustomUserDetails(
        memberId, null, Provider.NAVER, Status.ACTIVE
    );

    given(memberRepository.getActiveMemberOrThrow(userDetails.memberId())).willReturn(member);

    memberService.withdraw(userDetails);

    assertThat(member.getStatus()).isEqualTo(Status.WITHDRAW);

    //임시
    verify(oauthUnlinkService, times(1)).unlink(Provider.NAVER, null);
  }

  @Test
  @DisplayName("구글 회원 탈퇴 성공")
  void withdrawGoogle() {
    Long memberId = 3L;
    Language language = new Language(3L, "JP");
    Member member = new Member(memberId, Provider.GOOGLE, null, "nickname", "profile",
        LocalDateTime.now(), LocalDateTime.now(), Status.ACTIVE, language, null);

    CustomUserDetails userDetails = new CustomUserDetails(
        memberId, null, Provider.GOOGLE, Status.ACTIVE
    );

    given(memberRepository.getActiveMemberOrThrow(userDetails.memberId())).willReturn(member);

    memberService.withdraw(userDetails);

    assertThat(member.getStatus()).isEqualTo(Status.WITHDRAW);

    // 임시
    verify(oauthUnlinkService, times(1)).unlink(Provider.GOOGLE, null);
  }

  @Test
  @DisplayName("회원 탈퇴 실패 - 존재하지 않는 사용자")
  void withdrawFailNotFoundUser() {
    Long memberId = 99L;
    CustomUserDetails userDetails = new CustomUserDetails(
        memberId, "no", Provider.KAKAO, Status.ACTIVE
    );

    given(memberRepository.getActiveMemberOrThrow(userDetails.memberId())).willReturn(null);

    org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
      memberService.withdraw(userDetails);
    });
  }

  @Test
  @DisplayName("회원 탈퇴 실패 - 소셜 연동 해제 실패")
  void withdrawFailUnlinkError() {
    Long memberId = 4L;
    Language language = new Language(4L, "JP");
    Member member = new Member(memberId, Provider.KAKAO, "kakao", "nickname", "profile",
        LocalDateTime.now(), LocalDateTime.now(), Status.ACTIVE, language, null);

    CustomUserDetails userDetails = new CustomUserDetails(
        memberId, "kakao", Provider.KAKAO, Status.ACTIVE
    );

    given(memberRepository.getActiveMemberOrThrow(userDetails.memberId())).willReturn(member);

    willThrow(new RuntimeException("연동 해제 실패"))
        .given(oauthUnlinkService)
        .unlink(Provider.KAKAO, "kakao");

    org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
      memberService.withdraw(userDetails);
    });

    assertThat(member.getStatus()).isEqualTo(Status.ACTIVE);
  }
}
