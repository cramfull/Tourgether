package com.tourgether.tourgether.auth.oauth.service;

import com.tourgether.tourgether.auth.oauth.CustomOAuth2User;
import com.tourgether.tourgether.auth.oauth.userinfo.OAuth2UserInfo;
import com.tourgether.tourgether.auth.oauth.userinfo.OAuth2UserInfoFactory;
import com.tourgether.tourgether.language.entity.Language;
import com.tourgether.tourgether.member.entity.Member;
import com.tourgether.tourgether.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import java.util.Arrays;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.security.core.AuthenticationException;
import com.tourgether.tourgether.member.enums.Provider;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final MemberRepository memberRepository;
  private final EntityManager entityManager;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);
    try {
      return this.processOAuth2User(userRequest, oAuth2User);
    } catch (AuthenticationException ex) {
      log.debug("Authentication error: {}", ex);
      throw ex;
    } catch (Exception ex) {
      log.debug("InternalAuthenticationService error {}", ex);
      throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
    }
  }

  private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
    Provider provider = getProviderType(
        oAuth2UserRequest.getClientRegistration().getRegistrationId());
    Map<String, Object> attributes = oAuth2User.getAttributes();

    OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider, attributes);

    Member member = getOrCreateMember(provider, userInfo);

    return new CustomOAuth2User(member);
  }


  private Provider getProviderType(String registrationId) {
    return Arrays.stream(Provider.values())
        .filter(provider -> provider.getProviderType().equalsIgnoreCase(registrationId))
        .findFirst()
        .orElseThrow(RuntimeException::new);
    // TODO Exception 종류 생각
  }

  private Member getOrCreateMember(Provider provider, OAuth2UserInfo userInfo) {
    Member member = memberRepository.findByProviderAndProviderId(provider, userInfo.getProviderId())
        .orElse(memberRepository.save(
                Member.builder()
                    .provider(provider)
                    .providerId(userInfo.getProviderId())
                    .nickname(userInfo.getNickName())
                    .profileImage(userInfo.getProfileImage())
                    .languageId(entityManager.getReference(Language.class, 2)).build())
        );
    // TODO Languae는 프록시 객체로 임시 저장
    return member;
  }
}
