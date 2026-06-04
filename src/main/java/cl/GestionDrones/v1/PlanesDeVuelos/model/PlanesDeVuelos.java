package cl.GestionDrones.v1.PlanesDeVuelos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "planes_de_vuelos")
public class PlanesDeVuelos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "run_piloto", nullable = false, length = 20)
    private String runPiloto; 

    @Column(name = "patente_dron", nullable = false, length = 50)
    private String patenteDron; 

    @Column(name = "fecha_estimada_vuelo", nullable = false)
    private LocalDate fechaEstimadaVuelo;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column(name = "coordenadas_origen", nullable = false, length = 255)
    private String coordenadasOrigen;

    @Column(name = "coordenadas_destino", nullable = false, length = 255)
    private String coordenadasDestino;

    @Column(name = "altitud_maxima_mt", nullable = false)
    private Double altitudMaximaMt;

    @Column(name = "rut_contratista", nullable = false, length = 20)
    private String rutContratista;

    public PlanesDeVuelos() {}

    
    public PlanesDeVuelos(Long id, String runPiloto, String patenteDron, LocalDate fechaEstimadaVuelo, 
                          LocalTime horaInicio, LocalTime horaFin, String coordenadasOrigen, 
                          String coordenadasDestino, Double altitudMaximaMt, String rutContratista) {
        this.id = id;
        this.runPiloto = runPiloto;
        this.patenteDron = patenteDron;
        this.fechaEstimadaVuelo = fechaEstimadaVuelo;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.coordenadasOrigen = coordenadasOrigen;
        this.coordenadasDestino = coordenadasDestino;
        this.altitudMaximaMt = altitudMaximaMt;
        this.rutContratista = rutContratista; // Asignación del nuevo campo
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRunPiloto() { return runPiloto; }
    public void setRunPiloto(String runPiloto) { this.runPiloto = runPiloto; }

    public String getPatenteDron() { return patenteDron; }
    public void setPatenteDron(String patenteDron) { this.patenteDron = patenteDron; }

    public LocalDate getFechaEstimadaVuelo() { return fechaEstimadaVuelo; }
    public void setFechaEstimadaVuelo(LocalDate fechaEstimadaVuelo) { this.fechaEstimadaVuelo = fechaEstimadaVuelo; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public String getCoordenadasOrigen() { return coordenadasOrigen; }
    public void setCoordenadasOrigen(String coordenadasOrigen) { this.coordenadasOrigen = coordenadasOrigen; }

    public String getCoordenadasDestino() { return coordenadasDestino; }
    public void setCoordenadasDestino(String coordenadasDestino) { this.coordenadasDestino = coordenadasDestino; }

    public Double getAltitudMaximaMt() { return altitudMaximaMt; }
    public void setAltitudMaximaMt(Double altitudMaximaMt) { this.altitudMaximaMt = altitudMaximaMt; }

    
    public String getRutContratista() { return rutContratista; }
    public void setRutContratista(String rutContratista) { this.rutContratista = rutContratista; }
}