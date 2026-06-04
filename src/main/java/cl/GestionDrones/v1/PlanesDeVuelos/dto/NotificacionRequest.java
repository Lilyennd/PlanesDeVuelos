package cl.GestionDrones.v1.PlanesDeVuelos.dto;

import java.time.LocalDateTime;

public record NotificacionRequest (
    String tipoDestinatario,  
    Long idDestinatario,       
    String tipoNotificacion,   
    String mensaje,            
    String estado,             
    LocalDateTime fechaCreacion){

}
