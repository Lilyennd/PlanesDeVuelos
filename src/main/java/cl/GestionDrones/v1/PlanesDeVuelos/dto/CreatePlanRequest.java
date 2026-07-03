package cl.GestionDrones.v1.PlanesDeVuelos.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record CreatePlanRequest(
    @NotBlank(message = "El RUN del piloto es obligatorio")
    String runPiloto,

    @NotBlank(message = "La patente del dron es obligatoria")
    String patenteDron,
     
    @FutureOrPresent(message = "La fecha del vuelo debe ser hoy o en el futuro")
    @NotNull(message = "La fecha estimada de vuelo es obligatoria")
    LocalDate fechaEstimadaVuelo,

    @NotNull(message = "La hora de inicio es obligatoria")
    LocalTime horaInicio,

    @NotNull(message = "La hora de fin es obligatoria")
    LocalTime horaFin,

    @NotBlank(message = "Las coordenadas de origen son obligatorias")
    String coordenadasOrigen,

    @NotBlank(message = "Las coordenadas de destino son obligatorias")
    String coordenadasDestino,

    @NotNull(message = "La altitud máxima es obligatoria")
    Double altitudMaximaMt,

    @NotBlank(message = "El RUT del contratista es obligatorio")
    String rutContratista
) {}