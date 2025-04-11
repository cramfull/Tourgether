package com.tourgether.tourgether.auth.controller;

import com.tourgether.tourgether.auth.dto.RefreshTokenRequest;
import com.tourgether.tourgether.auth.dto.TokenResponse;
import com.tourgether.tourgether.auth.service.AuthService;
import com.tourgether.tourgether.common.dto.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/reissue")
  public ResponseEntity<ApiResult<TokenResponse>> reissue(
      @RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
    TokenResponse tokenResponse = authService.reissueToken(refreshTokenRequest.refreshToken());
    return ResponseEntity.ok(ApiResult.success(tokenResponse));
  }
}
