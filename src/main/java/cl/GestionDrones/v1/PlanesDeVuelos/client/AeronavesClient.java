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
            // Como tu AeronaveController devuelve una lista pura, 
            // le decimos a Spring que lo convierta en un Arreglo (Array) de DTOs: SeguroAeronaveDto[].class
            SeguroAeronaveDto[] response = webClient.get()
                    .uri("/patente/{patente}", patente) 
                    .retrieve()
                    .bodyToMono(SeguroAeronaveDto[].class) 
                    .block();

            // Si el arreglo no es nulo y trae al menos un elemento, devolvemos el primero
            if (response != null && response.length > 0) {
                return response[0]; 
            }
            return null;

        } catch (WebClientResponseException.NotFound e) {
            // Atrapamos el error 404 que devuelve tu AeronaveController si no encuentra la patente
            return null; 
        }
    }
}