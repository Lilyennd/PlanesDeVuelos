package cl.GestionDrones.v1.PlanesDeVuelos.client;

import cl.GestionDrones.v1.PlanesDeVuelos.dto.CertificadoPilotosDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class PilotosClient {

    private final WebClient webClient;

    
    public PilotosClient(@Qualifier("pilotosWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

        public CertificadoPilotosDto obtenerPorRun(String run) {
        return webClient.get()
                .uri("/run/" + run)
                .retrieve()
                .bodyToMono(CertificadoPilotosDto.class)
                .block();
    }
}