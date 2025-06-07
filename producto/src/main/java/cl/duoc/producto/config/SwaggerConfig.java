package cl.duoc.producto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservicios Perfulandia")
                        .version("1.0")
                        .description("Docuentación de la API de Productos para Perfulandia")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo"))
                        );
                        }

}
