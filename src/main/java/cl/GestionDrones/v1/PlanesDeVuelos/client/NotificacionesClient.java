package cl.GestionDrones.v1.PlanesDeVuelos.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import cl.GestionDrones.v1.PlanesDeVuelos.dto.NotificacionRequest;

@Component
public class NotificacionesClient {

    private final WebClient webClient;

    public NotificacionesClient(@Qualifier("notificacionesWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public void enviarNotificacion(NotificacionRequest notificacion) {
        webClient.post()
                .uri("/")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(notificacion)
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorComplete()
                .block();
    }
}
