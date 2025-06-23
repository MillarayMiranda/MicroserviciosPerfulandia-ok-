package cl.duoc.cupon.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${springdoc.version:1.0.0}")
    private String apiVersion;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Cupon-Service - Gestión de Cupones")
                .version(apiVersion)
                .description("Documentación API para el módulo de cupones de descuento")
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }

    @Bean
    public GroupedOpenApi cuponApi() {
        return GroupedOpenApi.builder()
            .group("cupones")
            .pathsToMatch("/cupones/**") // Coincide con tu @RequestMapping base
            .build();
    }
}