package com.tourgether.tourgether.auth.controller;

import com.tourgether.tourgether.auth.CustomUserDetails;
import com.tourgether.tourgether.auth.dto.OAuth2LoginRequest;
import com.tourgether.tourgether.auth.dto.RefreshTokenRequest;
import com.tourgether.tourgether.auth.dto.TokenResponse;
import com.tourgether.tourgether.auth.service.AuthService;
import com.tourgether.tourgether.auth.util.HeaderUtil;
import com.tourgether.tourgether.common.dto.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

  private final AuthService authService;
  private final HeaderUtil headerUtil;

  @PostMapping("/oauth2/{provider}/login")
  public ResponseEntity<ApiResult<TokenResponse>> oauth2Login(
          @PathVariable String provider,
          @RequestBody @Valid OAuth2LoginRequest oAuth2LoginRequest) {
    TokenResponse tokenResponse = authService.login(provider, oAuth2LoginRequest.socialAccessToken());
    return ResponseEntity.ok(ApiResult.success(tokenResponse));
  }

  @PostMapping("/auth/reissue")
  public ResponseEntity<ApiResult<TokenResponse>> reissue(
      @RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
    TokenResponse tokenResponse = authService.reissueToken(refreshTokenRequest.refreshToken());
    return ResponseEntity.ok(ApiResult.success(tokenResponse));
  }

  @PostMapping("/auth/logout")
  public ResponseEntity<ApiResult<Void>> logout(
          HttpServletRequest request,
          @AuthenticationPrincipal CustomUserDetails userDetails) {
    String accessToken = headerUtil.resolveToken(request);
    authService.logout(accessToken, userDetails.memberId());
    return ResponseEntity.ok(ApiResult.success(null, HttpStatus.NO_CONTENT.value(), "정상 로그아웃"));
  }

  @GetMapping("/auth/me")
  public ResponseEntity<ApiResult<Void>> validateToken(
          @AuthenticationPrincipal CustomUserDetails userDetails){
    return ResponseEntity.ok(ApiResult.success(null));
  }
}
