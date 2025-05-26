package cl.dev.mmatush.moviesapp.configuration;

import cl.dev.mmatush.moviesapp.configuration.property.ApiKeyProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

    private final ApiKeyProperties apiKeyProperties;

    @Value("${info.app.name}")
    private String appName;

    @Value("${info.app.version}")
    private String appVersion;

    @Value("${info.app.description}")
    private String appDescription;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(appName)
                        .version(appVersion)
                        .description("Documentación de " + appDescription))
                .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList(apiKeyProperties.getApiKey()))
                .components(new Components()
                        .addSecuritySchemes(apiKeyProperties.getApiKey(),
                                new io.swagger.v3.oas.models.security.SecurityScheme()
                                        .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.APIKEY)
                                        .in(io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER)
                                        .name(apiKeyProperties.getApiKey())));
    }
}
