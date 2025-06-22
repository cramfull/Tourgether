package com.tourgether.tourgether.auth.exception;

import com.tourgether.tourgether.common.exception.UnauthorizedException;

public class InvalidTokenException extends UnauthorizedException {

  public InvalidTokenException(String message) {
    super(message);
  }
}
