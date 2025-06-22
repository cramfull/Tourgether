package com.tourgether.tourgether.auth.oauth.userinfo;

import com.tourgether.tourgether.member.enums.Provider;
import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo{

  public NaverOAuth2UserInfo(Map<String, Object> attributes) {
    super(attributes);
  }

  @Override
  public String getProvider() {
    return Provider.KAKAO.name();
  }

  @Override
  public String getProviderId() {
    return (String) attributes.get("sub");
  }

  @Override
  public String getNickName() {
    return (String) attributes.get("nickname");
  }

  @Override
  public String getProfileImage() {
    return (String) attributes.get("profile_image_url");
  }

}
