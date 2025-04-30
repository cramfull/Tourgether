package com.tourgether.tourgether.member.controller;

import com.tourgether.tourgether.auth.CustomUserDetails;
import com.tourgether.tourgether.common.dto.ApiResult;
import com.tourgether.tourgether.member.dto.request.LanguageUpdateRequest;
import com.tourgether.tourgether.member.dto.request.NicknameUpdateRequest;
import com.tourgether.tourgether.member.dto.request.WithdrawRequest;
import com.tourgether.tourgether.member.dto.response.MemberInfoResponse;
import com.tourgether.tourgether.member.dto.response.NicknameUpdateResponse;
import com.tourgether.tourgether.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/me")
public class MemberController {

  private final MemberService memberService;

  @DeleteMapping
  public ResponseEntity<ApiResult<Void>> withdraw(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody @Valid WithdrawRequest withdrawRequest
  ) {
    memberService.withdraw(userDetails.memberId(), withdrawRequest.socialAccessToken());
    return ResponseEntity.ok(ApiResult.success(null));
  }

  @PatchMapping("/languages")
  public ResponseEntity<ApiResult<Void>> updateLanguage(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody @Valid LanguageUpdateRequest languageUpdateRequest) {

    memberService.updateLanguage(1L, languageUpdateRequest);
    return ResponseEntity.ok(ApiResult.success(null));
  }

  @PatchMapping("/nickname")
  public ResponseEntity<ApiResult<NicknameUpdateResponse>> updateNickname(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody @Valid NicknameUpdateRequest nicknameUpdateRequest) {

    NicknameUpdateResponse response = memberService.updateNickname(userDetails.memberId(),
        nicknameUpdateRequest.nickname());

    return ResponseEntity.ok(ApiResult.success(response));
  }

  @GetMapping
  public ResponseEntity<ApiResult<MemberInfoResponse>> getMemberInfo(
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    MemberInfoResponse response = memberService.getMemberInfo(1L);
    return ResponseEntity.ok(ApiResult.success(response));
  }
}
