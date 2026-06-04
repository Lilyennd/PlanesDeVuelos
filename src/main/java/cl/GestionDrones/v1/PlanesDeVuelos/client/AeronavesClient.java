package cl.GestionDrones.v1.PlanesDeVuelos.client;

import cl.GestionDrones.v1.PlanesDeVuelos.dto.SeguroAeronaveDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AeronavesClient {

    private final WebClient webClient;

    public AeronavesClient(@Qualifier("aeronavesWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public SeguroAeronaveDto obtenerSeguro(Long idAeronave) {
        return webClient.get()
                .uri("/{id}/seguro", idAeronave)
                .retrieve()
                .bodyToMono(SeguroAeronaveDto.class)
                .block();
    }

    public SeguroAeronaveDto obtenerPorPatente(String patente) {
        return webClient.get()
                .uri("/patente/" + patente)
                .retrieve()
                .bodyToMono(SeguroAeronaveDto.class)
                .block();
    }
}