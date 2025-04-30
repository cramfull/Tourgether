package com.tourgether.tourgether.language.exception;

import com.tourgether.tourgether.common.exception.ResourceNotFoundException;

public class LanguageNotFoundException extends ResourceNotFoundException {

  public LanguageNotFoundException(String message) {
    super(message);
  }
}
