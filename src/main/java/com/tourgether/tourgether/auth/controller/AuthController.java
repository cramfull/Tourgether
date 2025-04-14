package com.tourgether.tourgether.auth.controller;

import com.tourgether.tourgether.auth.dto.OAuth2LoginRequest;
import com.tourgether.tourgether.auth.dto.RefreshTokenRequest;
import com.tourgether.tourgether.auth.dto.TokenResponse;
import com.tourgether.tourgether.auth.service.AuthService;
import com.tourgether.tourgether.common.dto.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/oauth2/{provider}/login")
  public ResponseEntity<ApiResult<TokenResponse>> oauth2Login(
          @PathVariable String provider,
          @RequestBody @Valid OAuth2LoginRequest oAuth2LoginRequest) {
    TokenResponse tokenResponse = authService.login(provider, oAuth2LoginRequest.accessToken());
    return ResponseEntity.ok(ApiResult.success(tokenResponse));
  }

  @PostMapping("/auth/reissue")
  public ResponseEntity<ApiResult<TokenResponse>> reissue(
      @RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
    TokenResponse tokenResponse = authService.reissueToken(refreshTokenRequest.refreshToken());
    return ResponseEntity.ok(ApiResult.success(tokenResponse));
  }
}
