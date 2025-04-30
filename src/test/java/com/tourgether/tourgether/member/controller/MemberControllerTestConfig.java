package com.tourgether.tourgether.member.controller;

import com.tourgether.tourgether.member.service.MemberService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MemberControllerTestConfig {

    @Bean
    public MemberService memberService() {
        return Mockito.mock(MemberService.class);
    }
}
