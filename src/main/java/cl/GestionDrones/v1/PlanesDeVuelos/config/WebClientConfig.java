package cl.GestionDrones.v1.PlanesDeVuelos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${api.url.pilotos}")
    private String pilotosUrl;

    @Value("${api.url.aeronaves}")
    private String aeronavesUrl;

    @Value("${api.url.zonasrestringidas}")
    private String zonasUrl;

    @Value("${api.url.notificaciones}")
    private String notificacionesUrl;

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
    
    @Bean
    public WebClient pilotosWebClient(WebClient.Builder builder) {
        return builder.baseUrl(pilotosUrl).build();
    }

    @Bean
    public WebClient aeronavesWebClient(WebClient.Builder builder) {
        return builder.baseUrl(aeronavesUrl).build();
    }

    @Bean
    public WebClient zonasWebClient(WebClient.Builder builder) {
        return builder.baseUrl(zonasUrl).build();
    }
  
    @Bean
    public WebClient notificacionesWebClient(WebClient.Builder builder) {
        return builder.baseUrl(notificacionesUrl).build();
    }
}