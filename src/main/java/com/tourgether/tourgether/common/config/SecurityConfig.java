package com.tourgether.tourgether.common.config;

import com.tourgether.tourgether.auth.filter.JwtAuthenticationFilter;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(
                        (cors) ->
                                cors
                                        .configurationSource(corsConfigurationSource()))
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
                                                "/swagger-ui.html",
                                                "/api/v1/oauth2/**",
                                                "/api/v1/auth/reissue")
                                        .permitAll().anyRequest().authenticated()
                )

                .headers((headers) ->
                        headers
                                .frameOptions(FrameOptionsConfig::disable)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        //허용할 url 설정
        configuration.addAllowedOrigin("http://localhost:5173");
        configuration.addAllowedOrigin("https://www.tourgether.site");

        //허용할 헤더 설정
        configuration.addAllowedHeader("*");
        //허용할 http method
        configuration.addAllowedMethod("*");
        //사용자 자격 증명이 지원되는지 여부
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
