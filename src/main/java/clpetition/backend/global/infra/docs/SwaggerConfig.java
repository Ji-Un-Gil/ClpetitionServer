package clpetition.backend.global.infra.docs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@OpenAPIDefinition(
        info = @Info(
                title = "Clpetition API Docs",
                description = "클피티션 API 명세서",
                version = "v0.1.5"
        )
)
@Configuration
public class SwaggerConfig {

    private final String defaultAccessToken = "Bearer ";

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("AccessToken");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("AccessToken", securityScheme))
                .security(Arrays.asList(securityRequirement));
    }
}
