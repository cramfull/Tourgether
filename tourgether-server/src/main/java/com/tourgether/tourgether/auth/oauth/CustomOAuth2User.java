package com.tourgether.tourgether.auth.oauth;

import com.tourgether.tourgether.member.entity.Member;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
public class CustomOAuth2User implements OAuth2User {

  private final Member member;

  public CustomOAuth2User(Member member) {
    this.member = member;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return Collections.singletonMap("id", member.getId());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getName() {
    return String.valueOf(member.getId());
  }
}
