package com.tourgether.tourgether.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record WithdrawRequest (
        @NotBlank(message = "인증 플랫폼의 토큰이 비어있습니다. 다시 시도하세요.")
        String socialAccessToken
){
}
