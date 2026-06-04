package cl.GestionDrones.v1.PlanesDeVuelos.dto;

import java.time.LocalDate;

public class CertificadoPilotosDto {
    
    private String numeroLicencia;
    private boolean vigente;
    private LocalDate fechaVencimiento;

    
    public CertificadoPilotosDto() {}

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    public boolean isVigente() {
        return vigente;
    }

    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}