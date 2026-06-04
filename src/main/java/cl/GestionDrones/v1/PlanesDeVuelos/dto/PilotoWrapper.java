package cl.GestionDrones.v1.PlanesDeVuelos.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PilotoWrapper {
    
    private CertificadoPilotosDto datos;

    public CertificadoPilotosDto getDatos() {
        return datos;
    }

    public void setDatos(CertificadoPilotosDto datos) {
        this.datos = datos;
    }
}
