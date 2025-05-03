package com.tourgether.tourgether.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class GlobalCorsConfig {

  @Bean
  public CorsFilter corsFilter() {
    CorsConfiguration config = new CorsConfiguration();
    // 크리덴셜(true)이므로 와일드카드 대신 패턴 사용
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
    // 전체 경로에 적용
    src.registerCorsConfiguration("/**", config);
    return new CorsFilter(src);
  }

  @Bean
  public FilterRegistrationBean<CorsFilter> corsFilterRegistration(CorsFilter filter) {
    FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(filter);
    // 스프링 시큐리티 필터보다 우선순위 높게 실행
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return bean;
  }
}
