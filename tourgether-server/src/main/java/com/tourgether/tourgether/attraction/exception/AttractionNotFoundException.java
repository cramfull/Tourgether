package com.tourgether.tourgether.attraction.exception;

import com.tourgether.tourgether.common.exception.ResourceNotFoundException;

public class AttractionNotFoundException extends ResourceNotFoundException {

  public AttractionNotFoundException(String message) {
    super(message);
  }
}
