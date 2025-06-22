package com.tourgether.tourgether.common.exception;

public abstract class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String message) {
    super(message);
  }
}
