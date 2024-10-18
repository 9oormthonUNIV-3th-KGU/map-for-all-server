package api.goorm.map.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("cookieAuth", cookieSecurityScheme()))
                .info(apiInfo())
                .addSecurityItem(new SecurityRequirement().addList("cookieAuth"));
    }

    private SecurityScheme cookieSecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.COOKIE)
                .name("AccessToken");
    }

    private Info apiInfo() {
        return new Info()
                .title("산책")
                .description("GOORM KYONGGI UNIV TEAM 1 - 산책 REST API")
                .version("1.0.0");
    }
}
