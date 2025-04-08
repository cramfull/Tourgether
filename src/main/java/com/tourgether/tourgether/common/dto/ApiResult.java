package com.tourgether.tourgether.common.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResult<T> {

  private boolean success;
  private int code;
  private String message;
  private T data;

  public static <T> ApiResult<T> success(T data) {
    return new ApiResult<>(true, 200, "요청 성공", data);
  }

  public static <T> ApiResult<T> success(T data, int code, String message) {
    return new ApiResult<>(true, code, message, data);
  }

  public static ApiResult<?> fail(int code, String errorMessage) {
    return new ApiResult<>(false, code, errorMessage, null);
  }
}
