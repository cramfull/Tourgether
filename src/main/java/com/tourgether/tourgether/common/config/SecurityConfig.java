package com.tourgether.tourgether.common.config;

import com.tourgether.tourgether.auth.filter.JwtAuthenticationFilter;
import com.tourgether.tourgether.auth.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.tourgether.tourgether.auth.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.tourgether.tourgether.auth.oauth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final CustomOAuth2UserService customOAuth2UserService;
  private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
  private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(
            AbstractHttpConfigurer::disable
        )
        .sessionManagement((sessionManagement) ->
            sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )

        .authorizeHttpRequests(
            (auth) ->
                auth
                    .requestMatchers(
                        "/h2-console/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-ui.html")
                    .permitAll().anyRequest().authenticated()
        )

        .oauth2Login((oauth2) ->
            oauth2
                .userInfoEndpoint((userInfo) ->
                    userInfo
                        .userService(customOAuth2UserService)
                )
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler)
        )
        .headers((headers) ->
            headers
                .frameOptions(FrameOptionsConfig::disable)
        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
