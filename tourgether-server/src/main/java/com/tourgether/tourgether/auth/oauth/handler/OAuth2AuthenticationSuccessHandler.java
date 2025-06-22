package com.tourgether.tourgether.auth.oauth.handler;

import com.tourgether.tourgether.auth.dto.TokenResponse;
import com.tourgether.tourgether.auth.oauth.CustomOAuth2User;
import com.tourgether.tourgether.auth.service.AuthService;
import com.tourgether.tourgether.auth.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final AuthService authService;

    /**
     * 사용자 인증 성공 시 실행 핸들러
     * @param request 로그인 -> 인증 후 요청 HttpServletRequest
     * @param response 로그인 -> 인증 후 요청 HttpServletResponse
     * @param authentication 인증 성공 시 user의 Authentication
     * @throws IOException IOException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        Long memberId = oAuth2User.getMember().getId();

        TokenResponse tokenResponse = authService.issueToken(memberId);

        // TODO 내 앱으로 딥링크 return Uri 지정
        String targetUrl = UriComponentsBuilder.fromUriString("")
                .queryParam("accessToken", tokenResponse.accessToken())
                .queryParam("refreshToken", tokenResponse.refreshToken())
                .build()
                .toUriString();

        log.info("Redirecting to: {}", targetUrl);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
