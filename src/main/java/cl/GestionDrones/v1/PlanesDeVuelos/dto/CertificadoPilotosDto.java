package cl.GestionDrones.v1.PlanesDeVuelos.dto;

import java.time.LocalDate;

public class CertificadoPilotosDto {

    private Integer id;
    private String run;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String numeroCertificadoDgac;
    private LocalDate fechaVencimientoCertificacion;

    public CertificadoPilotosDto() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getRun() { return run; }
    public void setRun(String run) { this.run = run; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getNumeroCertificadoDgac() { return numeroCertificadoDgac; }
    public void setNumeroCertificadoDgac(String numeroCertificadoDgac) { this.numeroCertificadoDgac = numeroCertificadoDgac; }

    public LocalDate getFechaVencimientoCertificacion() { return fechaVencimientoCertificacion; }
    public void setFechaVencimientoCertificacion(LocalDate fechaVencimientoCertificacion) { this.fechaVencimientoCertificacion = fechaVencimientoCertificacion; }

    public boolean isCertificadoVigente() {
        return fechaVencimientoCertificacion != null && fechaVencimientoCertificacion.isAfter(LocalDate.now());
    }
}