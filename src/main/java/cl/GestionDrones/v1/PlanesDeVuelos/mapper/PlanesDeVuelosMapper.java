package cl.GestionDrones.v1.PlanesDeVuelos.mapper;

import cl.GestionDrones.v1.PlanesDeVuelos.dto.CreatePlanRequest;
import cl.GestionDrones.v1.PlanesDeVuelos.dto.UpdatePlanRequest;
import cl.GestionDrones.v1.PlanesDeVuelos.model.PlanesDeVuelos;

public class PlanesDeVuelosMapper {

    public static PlanesDeVuelos toPlanes(CreatePlanRequest request) {
        return new PlanesDeVuelos(
            null, 
            request.runPiloto(),
            request.patenteDron(),
            request.fechaEstimadaVuelo(),
            request.horaInicio(),
            request.horaFin(),
            request.coordenadasOrigen(),
            request.coordenadasDestino(),
            request.altitudMaximaMt(),
            request.rutContratista()
        );
    }

    public static void updateEntityFromDto(UpdatePlanRequest dto, PlanesDeVuelos entity) {
        entity.setPatenteDron(dto.patenteDron());
        entity.setFechaEstimadaVuelo(dto.fechaEstimadaVuelo());
        entity.setHoraInicio(dto.horaInicio());
        entity.setHoraFin(dto.horaFin());
        entity.setCoordenadasOrigen(dto.coordenadasOrigen());
        entity.setCoordenadasDestino(dto.coordenadasDestino());
        entity.setAltitudMaximaMt(dto.altitudMaximaMt());
        entity.setRutContratista(dto.rutContratista());
    }
}
