package cl.GestionDrones.v1.PlanesDeVuelos.dto;

import java.time.LocalDate;

public class SeguroAeronaveDto {

    private Long id;
    private String patente;
    private String marca;
    private String modelo;
    private String estado;
    private LocalDate fechaVencimientoSeguro;

    public SeguroAeronaveDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getFechaVencimientoSeguro() { return fechaVencimientoSeguro; }
    public void setFechaVencimientoSeguro(LocalDate fechaVencimientoSeguro) { this.fechaVencimientoSeguro = fechaVencimientoSeguro; }

    
    public boolean isSeguroVigente() {
        return fechaVencimientoSeguro != null && fechaVencimientoSeguro.isAfter(LocalDate.now());
    }
}