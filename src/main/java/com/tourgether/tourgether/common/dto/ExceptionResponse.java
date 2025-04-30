package com.tourgether.tourgether.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "오류 응답 포맷")
public class ExceptionResponse {

  @Schema(description = "API 호출 성공 여부 (항상 false)", example = "false")
  private final boolean success;

  @Schema(description = "HTTP 상태 코드")
  private final int code;

  @Schema(description = "에러 메시지")
  private final String message;

  @Schema(description = "에러 시 null")
  private final Object data;

  public ExceptionResponse(int code, String message) {
    this.success = false;
    this.code = code;
    this.message = message;
    this.data = null;
  }
}

