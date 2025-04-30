package com.tourgether.tourgether.auth.exception;

import com.tourgether.tourgether.common.exception.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {

  public UserNotFoundException(String message) {
    super(message);
  }
}
