package com.tourgether.tourgether.member.dto.request;

import jakarta.validation.constraints.Size;

public record NicknameUpdateRequest(
    @Size(min = 1, max = 10, message = "닉네임은 1 ~ 10자로 입력해주세요.")
    String nickname
) {

}
