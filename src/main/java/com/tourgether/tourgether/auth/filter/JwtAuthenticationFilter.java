package com.tourgether.tourgether.auth.filter;

import com.tourgether.tourgether.auth.CustomUserDetails;
import com.tourgether.tourgether.auth.exception.UserNotFoundException;
import com.tourgether.tourgether.auth.service.AuthService;
import com.tourgether.tourgether.auth.util.HeaderUtil;
import com.tourgether.tourgether.auth.util.JwtUtil;
import com.tourgether.tourgether.member.entity.Member;
import com.tourgether.tourgether.member.exception.MemberNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final HeaderUtil headerUtil;
  private final AuthService authService;


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    
    // CORS 설정 헤더 인증 로직 없이 통과
    if (HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
      filterChain.doFilter(request, response);
      return;
    }

    // request에서 token 추출
    String token = headerUtil.resolveToken(request);

    // 토큰 없는 요청인 경우 필터 넘어감
    if (token == null) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      // tokenCode 추출
      String tokenCode = jwtUtil.getTokenCodeFromAccessToken(token);

      // tokenCode로 Member 조회
      Member member = authService.getMemberWithTokenCode(tokenCode);

      // SecurityContextHolder에 Authentication 저장
      CustomUserDetails userDetails = new CustomUserDetails(member);
      Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,
          null, userDetails.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(authentication);
      filterChain.doFilter(request, response);

    } catch (MemberNotFoundException e) {
      // Member 조회 실패 : 존재 하지 않거나 탈퇴한 회원임
      log.debug("Member 조회 실패 : 존재 하지 않거나 탈퇴한 회원임");
      response.setStatus(HttpServletResponse.SC_GONE);
      return;
    } catch (UserNotFoundException e) {
      // redis 접근시 토큰이 만료되었거나, 맵핑 오류인 경우
      log.debug("redis expired or not found mapping");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    } catch (ExpiredJwtException e) {
      // 토큰이 만료된 경우
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    } catch (JwtException | IllegalArgumentException e) {
      // 토큰 형식이 이상한 경우
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      return;
    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return false;
  }
}
