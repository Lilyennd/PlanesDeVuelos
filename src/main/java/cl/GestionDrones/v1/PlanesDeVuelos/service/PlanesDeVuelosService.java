package cl.GestionDrones.v1.PlanesDeVuelos.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import cl.GestionDrones.v1.PlanesDeVuelos.dto.CreatePlanRequest;
import cl.GestionDrones.v1.PlanesDeVuelos.dto.UpdatePlanRequest;
import cl.GestionDrones.v1.PlanesDeVuelos.exception.FechaInvalidaException;
import cl.GestionDrones.v1.PlanesDeVuelos.exception.ResourceNotFoundException;
import cl.GestionDrones.v1.PlanesDeVuelos.mapper.PlanesDeVuelosMapper;
import cl.GestionDrones.v1.PlanesDeVuelos.model.PlanesDeVuelos;
import cl.GestionDrones.v1.PlanesDeVuelos.repository.PlanesDeVuelosRepository;

import org.springframework.stereotype.Service;

@Service
public class PlanesDeVuelosService {
    @Autowired
    private PlanesDeVuelosRepository planesDeVuelosRepository;
    

    public List<PlanesDeVuelos> getPlanesDeVuelos() {
        return planesDeVuelosRepository.findAll();
    }


    public PlanesDeVuelos savePlanDeVuelo(CreatePlanRequest request) {
        if (request.horaInicio().isAfter(request.horaFin())) {
            throw new FechaInvalidaException("La hora de inicio no puede ser posterior a la hora de término.");
        }
        
        PlanesDeVuelos nuevoPlan = PlanesDeVuelosMapper.toPlanes(request);
        return planesDeVuelosRepository.save(nuevoPlan);
    }

    public PlanesDeVuelos getPlanDeVueloId(Long id) {
        return planesDeVuelosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el plan de vuelo con el ID: " + id));
    }

    public PlanesDeVuelos updatePlanDeVuelo(UpdatePlanRequest request) {
        if (request.horaInicio().isAfter(request.horaFin())) {
            throw new FechaInvalidaException("La hora de inicio no puede ser posterior a la hora de término.");
        }

        PlanesDeVuelos planExistente = planesDeVuelosRepository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar. No existe el plan de vuelo con el ID: " + request.id()));

        PlanesDeVuelosMapper.updateEntityFromDto(request, planExistente);
        return planesDeVuelosRepository.save(planExistente);
    }


    public String deletePlanDeVuelo(Long id) {
        if (!planesDeVuelosRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar. No existe el plan de vuelo con el ID: " + id);
        }
        planesDeVuelosRepository.deleteById(id);
        return "plan de vuelo eliminado";
    }

    
    public List<PlanesDeVuelos> getPlanesPorPiloto(String runPiloto) {
        return planesDeVuelosRepository.findByRunPiloto(runPiloto);
    }

    public List<PlanesDeVuelos> getPlanesPorDron(String patenteDron) {
        return planesDeVuelosRepository.findByPatenteDron(patenteDron);
    }
    public int totalPlanesDeVuelos() {
        return planesDeVuelosRepository.findAll().size();
    }
    
    public List<PlanesDeVuelos> getPlanesPorContratista(String rutContratista) {
    return planesDeVuelosRepository.findByRutContratista(rutContratista);
    }
}