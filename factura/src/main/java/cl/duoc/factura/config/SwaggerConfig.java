package cl.duoc.factura.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
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
                        .title("Factura-Service - Sistema de Facturación")
                        .version(apiVersion)
                        .description("Documentación API para el módulo de facturación"));
    }

    @Bean
    public GroupedOpenApi facturaApi() {
        return GroupedOpenApi.builder()
                .group("facturacion")
                .pathsToMatch("/facturas/**") // Coincide con tu @RequestMapping
                .build();
    }
}