package cl.GestionDrones.v1.PlanesDeVuelos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
    
    @Bean
    public WebClient pilotosWebClient(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8081/api/v1/pilotos").build();
    }


    @Bean
    public WebClient aeronavesWebClient(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8082/api/v1/aeronaves").build();
    }


    @Bean
    public WebClient zonasWebClient(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8090/api/v1/ZonasRestringidas").build();
    }

  
    @Bean
    public WebClient notificacionesWebClient(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8091/api/v1/notificaciones").build();
    }
}