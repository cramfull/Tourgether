package com.tourgether.tourgether.attraction.exception;

import com.tourgether.tourgether.common.exception.ResourceNotFoundException;

public class AttractionTranslationNotFoundException extends ResourceNotFoundException {

  public AttractionTranslationNotFoundException(String message) {
    super(message);
  }
}
