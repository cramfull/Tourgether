package com.tourgether.tourgether.auth.service;

import com.tourgether.tourgether.auth.dto.TokenResponse;
import com.tourgether.tourgether.auth.exception.InvalidTokenException;
import com.tourgether.tourgether.auth.exception.UserNotFoundException;
import com.tourgether.tourgether.auth.oauth.dto.OAuth2UserInfo;
import com.tourgether.tourgether.auth.oauth.strategy.OAuth2StrategyContext;
import com.tourgether.tourgether.auth.util.JwtUtil;
import com.tourgether.tourgether.language.entity.Language;
import com.tourgether.tourgether.member.entity.Member;
import com.tourgether.tourgether.member.exception.MemberNotFoundException;
import com.tourgether.tourgether.member.repository.MemberRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final TokenMappingService tokenMappingService;
    private final RefreshTokenService refreshTokenService;
    private final MemberRepository memberRepository;
    private final OAuth2StrategyContext oAuth2StrategyContext;
    private final EntityManager entityManager;

    private final long ACCESS_TOKEN_VALIDITY_TIME = 60 * 60 * 1000L; // 1시간
    private final long REFRESH_TOKEN_VALIDITY_TIME = 14 * 24 * 60 * 60 * 1000L; // 2주

    public TokenResponse login(String providerType, String providerAccessToken) {
        OAuth2UserInfo oAuth2UserInfo = oAuth2StrategyContext.getStrategy(providerType)
                .getOAuth2UserInfo(providerAccessToken);

        if (oAuth2UserInfo == null) {
            throw new UserNotFoundException(String.format("%s 소셜 유저 정보 조회에 실패했습니다.", providerType));
        }

        Member member = getOrCreateMember(oAuth2UserInfo);
        return issueToken(member.getId());
    }

    public TokenResponse reissueToken(String refreshToken) {
        // refreshToken 유효성 검증 및 추출
        String tokenCode;
        Member member;
        try {
            // refreshToken tokenCode 추출
            tokenCode = jwtUtil.getTokenCodeFromRefreshToken(refreshToken);
            member = getMemberWithTokenCode(tokenCode);
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            log.debug("refresh Token 만료");
            throw new InvalidTokenException("refresh Token이 만료되었습니다.");
        } catch (JwtException | IllegalArgumentException e) {
            // 토큰 형식이 이상한 경우
            throw new InvalidTokenException("토큰 형식이 잘못되었습니다.");
        } catch (UserNotFoundException e) {
            // redis 접근시 토큰이 만료되었거나, 맵핑 오류인 경우
            log.debug("redis expired or not found mapping");
            throw e;
        } catch (MemberNotFoundException e) {
            // Member 조회 실패 : 존재 하지 않거나 탈퇴한 회원임
            log.debug("Member 조회 실패 : 존재 하지 않거나 탈퇴한 회원임");
            throw e;
        }

        if (!refreshTokenService.validateRefreshToken(member.getId(), refreshToken)) {
            throw new InvalidTokenException("저장된 RefreshToken 과 일치하지 않습니다.");
        }

        tokenMappingService.deleteMappingByTokenCode(tokenCode);
        return issueToken(member.getId());
    }

    public TokenResponse issueToken(Long memberId) {
        String tokenCode = jwtUtil.getRandomKey();
        tokenMappingService.saveTokenCodeMapping(memberId, tokenCode, ACCESS_TOKEN_VALIDITY_TIME);

        String accessToken = jwtUtil.generateAccessToken(tokenCode, ACCESS_TOKEN_VALIDITY_TIME);
        String refreshToken = jwtUtil.generateRefreshToken(tokenCode, REFRESH_TOKEN_VALIDITY_TIME);

        refreshTokenService.saveRefreshToken(memberId, refreshToken, REFRESH_TOKEN_VALIDITY_TIME);

        return new TokenResponse(accessToken, refreshToken);
    }

    public Member getMemberWithTokenCode(String tokenCode) {
        // tokenMappingService 맵핑 조회
        Long memberId = tokenMappingService.getMemberIdByTokenCode(tokenCode).orElse(null);
        if (memberId == null) {
            throw new UserNotFoundException("redis expired or not found mapping, memberId= " + memberId);
        }
        return memberRepository.getActiveMemberOrThrow(memberId);
    }

    private Member getOrCreateMember(OAuth2UserInfo oAuth2UserInfo) {
        Member member = memberRepository.findByProviderAndProviderId(oAuth2UserInfo.providerType(), oAuth2UserInfo.providerId())
                .orElse(memberRepository.save(
                        Member.builder()
                                .provider(oAuth2UserInfo.providerType())
                                .providerId(oAuth2UserInfo.providerId())
                                .nickname(oAuth2UserInfo.nickname())
                                .profileImage(oAuth2UserInfo.profileImage())
                                .languageId(entityManager.getReference(Language.class, 2)).build())
                );
        // TODO Languae는 프록시 객체로 임시 저장
        return member;
    }
}

