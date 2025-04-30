package com.tourgether.tourgether.member.exception;

import com.tourgether.tourgether.common.exception.ResourceNotFoundException;

public class MemberNotFoundException extends ResourceNotFoundException {

  public MemberNotFoundException(String message) {
    super(message);
  }
}
