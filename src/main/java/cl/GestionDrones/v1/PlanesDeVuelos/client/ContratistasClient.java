package cl.GestionDrones.v1.PlanesDeVuelos.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Component
public class ContratistasClient {

    private final WebClient webClient;

    public ContratistasClient(@Qualifier("contratistasWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public boolean verificarContratistaExiste(String rut) {
        try {
            List<?> response = webClient.get()
                    .uri("/rut/{rut}", rut)
                    .retrieve()
                    .bodyToFlux(Object.class)
                    .collectList()
                    .block();

            return response != null && !response.isEmpty();
        } catch (Exception e) {
            System.out.println("🔴 Error al conectar con Contratistas: " + e.getMessage());
            return false;
        }
    }
}