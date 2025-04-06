package com.tourgether.tourgether.member.controller;

import com.tourgether.tourgether.auth.CustomUserDetails;
import com.tourgether.tourgether.member.enums.Provider;
import com.tourgether.tourgether.member.enums.Status;
import com.tourgether.tourgether.member.service.MemberService;
import com.tourgether.tourgether.security.SecurityConfigTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MemberController.class)
@Import({MemberControllerTestConfig.class, SecurityConfigTest.class})
public class MemberControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private MemberService memberService;

  @Test
  @DisplayName("회원 탈퇴 성공")
  @WithMockUser
  void withdraw_success() throws Exception {
    // given: CustomUserDetails 생성
    CustomUserDetails userDetails = new CustomUserDetails(
        1L,
        "providerId",
        Provider.KAKAO,
        Status.ACTIVE
    );

    Authentication auth = new TestingAuthenticationToken(userDetails, null);
    SecurityContextHolder.getContext().setAuthentication(auth);

    // when & then
    mvc.perform(delete("/api/v1/members/me")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    // verify: 서비스 계층 호출 여부
    Mockito.verify(memberService).withdraw(userDetails);
  }

}
