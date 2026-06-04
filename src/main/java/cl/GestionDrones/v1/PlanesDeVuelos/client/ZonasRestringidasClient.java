package cl.GestionDrones.v1.PlanesDeVuelos.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ZonasRestringidasClient {

    private final WebClient webClient;

    public ZonasRestringidasClient(@Qualifier("zonasWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Boolean verificarCoordenadas(String coordenadasOrigen, String coordenadasDestino) {
        Map<String, String> body = Map.of(
            "coordenadasOrigen", coordenadasOrigen,
            "coordenadasDestino", coordenadasDestino
        );

        return webClient.post()
                .uri("/verificar")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorReturn(false)
                .block();
    }
}