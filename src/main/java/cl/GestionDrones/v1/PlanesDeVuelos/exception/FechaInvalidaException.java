package cl.GestionDrones.v1.PlanesDeVuelos.exception;

public class FechaInvalidaException extends RuntimeException{
    public FechaInvalidaException(String mensaje) {
        super(mensaje);
    }

    public FechaInvalidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

}
