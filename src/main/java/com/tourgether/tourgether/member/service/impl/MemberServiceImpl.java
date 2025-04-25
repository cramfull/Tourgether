package com.tourgether.tourgether.member.service.impl;

import com.tourgether.tourgether.auth.CustomUserDetails;
import com.tourgether.tourgether.auth.oauth.strategy.OAuth2StrategyContext;
import com.tourgether.tourgether.auth.service.AuthService;
import com.tourgether.tourgether.auth.unlink.service.OauthUnlinkService;
import com.tourgether.tourgether.language.entity.Language;
import com.tourgether.tourgether.language.repository.LanguageRepository;
import com.tourgether.tourgether.member.dto.response.MemberInfoResponse;
import com.tourgether.tourgether.member.dto.response.NicknameUpdateResponse;
import com.tourgether.tourgether.member.entity.Member;
import com.tourgether.tourgether.member.exception.MemberNotFoundException;
import com.tourgether.tourgether.member.repository.MemberRepository;
import com.tourgether.tourgether.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;
  private final OauthUnlinkService oauthUnlinkService;
  private final AuthService authService;
  private final LanguageRepository languageRepository;

  @Transactional
  @Override
  public void withdraw(Long memberId, String socialAccessToken) {
    Member member = memberRepository.getMemberOrThrow(memberId);

    authService.unlink(member.getProvider().getProviderType(), socialAccessToken);

    member.withdraw();
  }

  @Transactional
  @Override
  public void updateLanguage(Long memberId, String languageCode) {
    Member member = memberRepository.getMemberOrThrow(memberId);

    Language language = languageRepository.findByLanguageCode(languageCode)
        .orElseThrow(() -> new RuntimeException("지원하지 않는 언어입니다."));

    member.updateLanguage(language);
  }

  @Transactional
  @Override
  public NicknameUpdateResponse updateNickname(Long memberId, String nickname) {
    Member member = memberRepository.getMemberOrThrow(memberId);

    member.updateNickname(nickname);

    return new NicknameUpdateResponse(member.getNickname());
  }

  @Override
  public MemberInfoResponse getMemberInfo(Long memberId) {
    Member member = memberRepository.getMemberOrThrow(memberId);
    return MemberInfoResponse.from(member);
  }
}
