package cl.GestionDrones.v1.PlanesDeVuelos.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.GestionDrones.v1.PlanesDeVuelos.client.AeronavesClient;
import cl.GestionDrones.v1.PlanesDeVuelos.client.NotificacionesClient;
import cl.GestionDrones.v1.PlanesDeVuelos.client.PilotosClient;
import cl.GestionDrones.v1.PlanesDeVuelos.client.ZonasRestringidasClient;
import cl.GestionDrones.v1.PlanesDeVuelos.dto.CertificadoPilotosDto;
import cl.GestionDrones.v1.PlanesDeVuelos.dto.CreatePlanRequest;
import cl.GestionDrones.v1.PlanesDeVuelos.dto.NotificacionRequest;
import cl.GestionDrones.v1.PlanesDeVuelos.dto.SeguroAeronaveDto;
import cl.GestionDrones.v1.PlanesDeVuelos.dto.UpdatePlanRequest;
import cl.GestionDrones.v1.PlanesDeVuelos.exception.FechaInvalidaException;
import cl.GestionDrones.v1.PlanesDeVuelos.exception.ResourceNotFoundException;
import cl.GestionDrones.v1.PlanesDeVuelos.mapper.PlanesDeVuelosMapper;
import cl.GestionDrones.v1.PlanesDeVuelos.model.PlanesDeVuelos;
import cl.GestionDrones.v1.PlanesDeVuelos.repository.PlanesDeVuelosRepository;

@Service
public class PlanesDeVuelosService {

    @Autowired
    private PlanesDeVuelosRepository planesDeVuelosRepository;

    @Autowired
    private PilotosClient pilotosClient;

    @Autowired
    private AeronavesClient aeronavesClient;

    @Autowired
    private ZonasRestringidasClient zonasRestringidasClient;

    @Autowired
    private NotificacionesClient notificacionesClient;

    public List<PlanesDeVuelos> getPlanesDeVuelos() {
        return planesDeVuelosRepository.findAll();
    }

    public PlanesDeVuelos getPlanDeVueloId(Long id) {
        return planesDeVuelosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el plan de vuelo con el ID: " + id));
    }

    public List<PlanesDeVuelos> getPlanesPorPiloto(String runPiloto) {
        return planesDeVuelosRepository.findByRunPiloto(runPiloto);
    }

    public List<PlanesDeVuelos> getPlanesPorDron(String patenteDron) {
        return planesDeVuelosRepository.findByPatenteDron(patenteDron);
    }

    public List<PlanesDeVuelos> getPlanesPorContratista(String rutContratista) {
        return planesDeVuelosRepository.findByRutContratista(rutContratista);
    }

    public int totalPlanesDeVuelos() {
        return planesDeVuelosRepository.findAll().size();
    }

    public PlanesDeVuelos savePlanDeVuelo(CreatePlanRequest request) {

        if (request.horaInicio().isAfter(request.horaFin())) {
            throw new FechaInvalidaException("La hora de inicio no puede ser posterior a la hora de término.");
        }

        // 1. Validar piloto — certificado DGAC vigente
        CertificadoPilotosDto piloto = pilotosClient.obtenerCertificado(request.runPiloto());
        if (piloto == null || !piloto.isCertificadoVigente()) {
            throw new RuntimeException("El piloto no existe o su certificado DGAC está vencido.");
        }

        // 2. Validar aeronave — seguro vigente
        SeguroAeronaveDto aeronave = aeronavesClient.obtenerSeguro(request.patenteDron());
        
        if (aeronave == null || !aeronave.isSeguroVigente()) {
            throw new RuntimeException("La aeronave no existe o su seguro está vencido.");
        }

        // 3. Validar zona restringida
        Boolean restringida = zonasRestringidasClient.verificarCoordenadas(
            request.coordenadasOrigen(),
            request.coordenadasDestino()
        );
        if (Boolean.TRUE.equals(restringida)) {
            throw new RuntimeException("El plan de vuelo cruza una zona restringida. No puede ser aprobado.");
        }

        // 4. Guardar el plan aprobado
        PlanesDeVuelos planGuardado = planesDeVuelosRepository.save(
            PlanesDeVuelosMapper.toPlanes(request)
        );

        // 5. Notificar
        notificacionesClient.enviarNotificacion(new NotificacionRequest(
            "CONTRATISTA",
            1L,
            "PLAN_APROBADO",
            "Su plan de vuelo ID " + planGuardado.getId() + " fue aprobado correctamente.",
            "PENDIENTE",
            LocalDateTime.now()
        ));

        return planGuardado;
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
}