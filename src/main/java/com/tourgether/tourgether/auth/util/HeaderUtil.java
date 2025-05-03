package com.tourgether.tourgether.auth.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Getter
@Component
public class HeaderUtil {

  private final String PREFIX_TOKEN_BEARER = "Bearer ";

  public String resolveToken(HttpServletRequest request){
    String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (bearerToken != null && bearerToken.startsWith(PREFIX_TOKEN_BEARER)) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
