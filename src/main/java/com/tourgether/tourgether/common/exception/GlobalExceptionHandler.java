package com.tourgether.tourgether.common.exception;

import com.tourgether.tourgether.common.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ApiResponse<?>> handleBadRequest(BadRequestException ex) {
    log.warn("[BadRequestException] {}", ex.getMessage());

    return ResponseEntity.badRequest()
        .body(ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiResponse<?>> handleNotFound(ResourceNotFoundException ex) {
    log.warn("[ResourceNotFoundException] {}", ex.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ApiResponse.fail(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<?>> handleValidationException(
      MethodArgumentNotValidException ex) {
    String message = ex.getBindingResult().getFieldErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining(", "));
    log.warn("[ValidationException] {}", message, ex);

    return ResponseEntity.badRequest()
        .body(ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), message));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<?>> handleGenericException(
      Exception ex) {

    log.error("[UnhandledException]", ex);

    return ResponseEntity.internalServerError()
        .body(ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
  }
}
