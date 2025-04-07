package com.tourgether.tourgether.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Tourgether API 문서")
            .version("v1.0.0")
            .description("서울 관광 플랫폼 Tourgether의 백엔드 API 문서입니다."));
  }

}
