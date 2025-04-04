package com.tourgether.tourgether.member.service.impl;

import com.tourgether.tourgether.auth.CustomUserDetails;
import com.tourgether.tourgether.auth.unlink.service.OauthUnlinkService;
import com.tourgether.tourgether.language.entity.Language;
import com.tourgether.tourgether.language.entity.repository.LanguageRepository;
import com.tourgether.tourgether.member.entity.Member;
import com.tourgether.tourgether.member.enums.Status;
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
    private final LanguageRepository languageRepository;

    @Transactional
    @Override
    public void withdraw(CustomUserDetails userDetails) {

        //TODO: GlobalExceptionHandler 확정 시 NotFoundException으로 변경
        Member member = memberRepository.findByIdAndStatus(userDetails.memberId(), Status.ACTIVE)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

        String identifier = switch (userDetails.provider()) {
            case KAKAO -> userDetails.providerId();

            // TODO: RN에서 토큰 전달받기 or 레디스에서 꺼내기 ---> oauth 구현 완료 후 정하기
            //case GOOGLE, NAVER -> getAccessToken(userDetails);
            case NAVER -> null;
            case GOOGLE -> null;
        };

        oauthUnlinkService.unlink(userDetails.provider(), identifier);
        member.withdraw();
    }

    @Transactional
    @Override
    public void updateLanguage(Long memberId, String languageCode) {
        Member member = memberRepository.findByIdAndStatus(memberId, Status.ACTIVE)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

        Language language = languageRepository.findByLanguageCode(languageCode)
            .orElseThrow(() -> new RuntimeException("지원하지 않는 언어입니다."));

        member.updateLanguage(language);
    }
}
