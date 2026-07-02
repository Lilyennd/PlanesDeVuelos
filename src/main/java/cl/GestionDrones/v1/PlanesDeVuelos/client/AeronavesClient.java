package cl.GestionDrones.v1.PlanesDeVuelos.client;


import cl.GestionDrones.v1.PlanesDeVuelos.dto.SeguroAeronaveDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class AeronavesClient {

    private final WebClient webClient;

  public AeronavesClient(@Qualifier("aeronavesWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public SeguroAeronaveDto obtenerSeguro(String patente) {
        try {
            return webClient.get()
                    .uri("/patente/{patente}", patente) 
                    .retrieve()
                    .bodyToMono(SeguroAeronaveDto.class) 
                    .block();

        } catch (WebClientResponseException.NotFound e) {
            return null; // Si devuelve un 404, retorna null
        } catch (Exception e) {
            System.out.println("Error al conectar con Aeronaves: " + e.getMessage());
            return null;
        }
    }
}