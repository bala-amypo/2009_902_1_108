package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Dynamic Event Ticket Pricing Engine API")
                        .version("1.0")
                        .description("API for managing dynamic event ticket pricing"))
                .servers(List.of(
                        new Server().url("https://9024.408procr.amypo.ai/").description("Production
                        new Server().url("http://localhost:8080").description("Local Development")
                ));
    }
}