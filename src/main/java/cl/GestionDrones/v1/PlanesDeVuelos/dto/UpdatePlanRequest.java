package cl.GestionDrones.v1.PlanesDeVuelos.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdatePlanRequest(
    @NotNull(message = "El ID del plan de vuelo es obligatorio para actualizar")
    Long id,

    @NotBlank(message = "El RUN del piloto es obligatorio")
    @Size(max = 20, message = "El RUN no puede superar los 20 caracteres")
    String runPiloto,

    @NotBlank(message = "La patente del dron es obligatoria")
    @Size(max = 50, message = "La patente no puede superar los 50 caracteres")
    String patenteDron,

    @NotNull(message = "La fecha estimada de vuelo es obligatoria")
    @FutureOrPresent(message = "La fecha del vuelo debe ser hoy o en el futuro")
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
) {
}