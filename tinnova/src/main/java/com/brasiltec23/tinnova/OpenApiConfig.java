package com.brasiltec23.tinnova;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Desafio TINNOVA")
                        .version("1.0.0")
                        .description("API de processo seletivo da TINNOVA"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("tinnova-public")
                .packagesToScan("com.brasiltec23.tinnova.api.controller")  // Especifica o pacote
                .pathsToMatch("/api/**")
                .build();
    }
}
