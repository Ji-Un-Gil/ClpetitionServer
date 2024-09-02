package clpetition.backend.global.infra.docs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.ArrayList;
import java.util.Arrays;

@OpenAPIDefinition(
        info = @Info(
                title = "Clpetition API Docs",
                description = "클피티션 API 명세서",
                version = "v0.4.0"
        )
)
@Configuration
public class SwaggerConfig {

    private final String defaultAccessToken = "Bearer ";

    @Bean
    public OpenAPI openAPI() {
        ArrayList<Server> servers = new ArrayList<>();
        servers.add(new Server().url("https://www.clpetition-server.shop/").description("prod"));
        servers.add(new Server().url("http://localhost:8080").description("dev"));

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("AccessToken");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("AccessToken", securityScheme))
                .servers(servers)
                .security(Arrays.asList(securityRequirement));
    }
}
