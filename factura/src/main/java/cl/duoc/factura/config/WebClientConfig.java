package cl.duoc.factura.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    // Configuración base para todos los WebClients
    private WebClient.Builder baseConfig(String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

    /* --- Beans esenciales para Factura --- */

    @Bean
    public WebClient webClientPedido() {
        return baseConfig("http://localhost:8085") // Pedido (8085)
                .filter(errorHandler("Error al comunicarse con Pedido"))
                .build();
    }

    @Bean
    public WebClient webClientUsuario() {
        return baseConfig("http://localhost:8080") // Usuario (8080)
                .filter(errorHandler("Error al comunicarse con Usuario"))
                .build();
    }

    @Bean
    public WebClient webClientProducto() {
        return baseConfig("http://localhost:8082") // Producto (8082)
                .build();
    }

    @Bean
    public WebClient webClientEnvio() {
        return baseConfig("http://localhost:8087") // Envío (8087)
                .build();
    }

    // Manejador genérico de errores
    private ExchangeFilterFunction errorHandler(String errorMessage) {
        return ExchangeFilterFunction.ofResponseProcessor(response -> {
            if (response.statusCode().isError()) {
                return Mono.error(new RuntimeException(errorMessage + ": " + response.statusCode()));
            }
            return Mono.just(response);
        });
    }
}