package com.tourgether.tourgether.member.controller;

import com.tourgether.tourgether.auth.CustomUserDetails;
import com.tourgether.tourgether.common.dto.ApiResponse;
import com.tourgether.tourgether.language.entity.Language;
import com.tourgether.tourgether.member.dto.request.LanguageUpdateRequest;
import com.tourgether.tourgether.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> withdraw(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        memberService.withdraw(userDetails);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PatchMapping("/languages")
    public ResponseEntity<ApiResponse<Void>> updateLanguage(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody @Valid LanguageUpdateRequest languageUpdateRequest) {

        memberService.updateLanguage(userDetails.memberId(), languageUpdateRequest.languageCode());
        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
