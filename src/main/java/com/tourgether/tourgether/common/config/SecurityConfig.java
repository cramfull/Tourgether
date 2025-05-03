package com.tourgether.tourgether.common.config;

import com.tourgether.tourgether.auth.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        // ① Security 내장 CorsFilter 활성화, 아래 corsConfigurationSource() 를 읽어갑니다
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))

        // ② CSRF 끄기
        .csrf(AbstractHttpConfigurer::disable)

        // ③ 세션 없이 JWT로 stateless 인증
        .sessionManagement(sm ->
            sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )

        // ④ 경로별 인가 설정
        .authorizeHttpRequests(auth ->
            auth
                // 인증 없이 풀 URL
                .requestMatchers(
                    "/h2-console/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html",
                    "/api/v1/oauth2/**",
                    "/api/v1/auth/reissue"
                ).permitAll()
                // Preflight(OPTIONS) 도 풀어주기
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 나머지 요청은 인증 필수
                .anyRequest().authenticated()
        )

        // ⑤ JWT 필터 등록
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  /**
   * Spring Security CORS 처리를 위한 정책 정의
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    // 크리덴셜(true)이므로 와일드카드 대신 패턴을 써야 합니다
    config.setAllowedOriginPatterns(List.of(
        "https://tourgether.shop",
        "https://www.tourgether.shop",
        "https://tourgether.site",
        "https://www.tourgether.site",
        "http://localhost:5173"
    ));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(List.of("*"));
    config.setAllowCredentials(true);
    config.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
    src.registerCorsConfiguration("/**", config);
    return src;
  }
}
