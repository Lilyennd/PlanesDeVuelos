package cl.GestionDrones.v1.PlanesDeVuelos.exception;

public class ResourceNotFoundException  extends RuntimeException{
    
     public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
    
    public ResourceNotFoundException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
