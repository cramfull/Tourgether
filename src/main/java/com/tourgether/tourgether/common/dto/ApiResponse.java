package com.tourgether.tourgether.common.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private boolean success;
    private int code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, 200, "요청 성공", data);
    }

    public static <T> ApiResponse<T> success(T data, int code, String message) {
        return new ApiResponse<>(true, code, message, data);
    }

    public static ApiResponse<?> fail(int code, String errorMessage) {
        return new ApiResponse<>(false, code, errorMessage, null);
    }
}
