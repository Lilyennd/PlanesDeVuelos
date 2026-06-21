package cl.GestionDrones.v1.PlanesDeVuelos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.GestionDrones.v1.PlanesDeVuelos.dto.CreatePlanRequest;
import cl.GestionDrones.v1.PlanesDeVuelos.dto.UpdatePlanRequest;
import cl.GestionDrones.v1.PlanesDeVuelos.model.PlanesDeVuelos;
import cl.GestionDrones.v1.PlanesDeVuelos.service.PlanesDeVuelosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Planes de Vuelo", description = "Operaciones relacionadas con la planificación y registro de planes de vuelo")
@RestController
@RequestMapping("/api/v1/planesDeVuelos")
public class PLanesDeVuelosController {

    @Autowired
    private PlanesDeVuelosService planesDeVuelosService;

    @Operation(summary = "Obtener todos los planes de vuelo", description = "Retorna una lista completa de todos los planes de vuelo registrados en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de planes de vuelo obtenida con éxito", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlanesDeVuelos.class)))
    })
    @GetMapping
    public ResponseEntity<List<PlanesDeVuelos>> getAllPlanes() {
        return new ResponseEntity<>(planesDeVuelosService.getPlanesDeVuelos(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener un plan de vuelo por ID", description = "Busca y retorna los detalles de un plan de vuelo específico utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Plan de vuelo encontrado con éxito", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlanesDeVuelos.class))),
        @ApiResponse(responseCode = "404", description = "Plan de vuelo no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PlanesDeVuelos> getPlanById(
        @Parameter(description = "ID único del plan de vuelo a consultar", required = true, example = "1")
        @PathVariable Long id
    ) {
        PlanesDeVuelos plan = planesDeVuelosService.getPlanDeVueloId(id);
        
        return ResponseEntity.ok(plan);
    }

    @Operation(summary = "Crear un nuevo plan de vuelo", description = "Registra un nuevo plan de vuelo en el sistema con validación previa de los datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Plan de vuelo creado exitosamente", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlanesDeVuelos.class))),
        @ApiResponse(responseCode = "400", description = "Existen errores de validación en los datos enviados", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createPlan(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Estructura JSON del nuevo plan de vuelo a registrar", required = true)
        @Valid @RequestBody CreatePlanRequest request, 
        BindingResult result
    ) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> 
                errores.put(error.getField(), error.getDefaultMessage())
            );
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        PlanesDeVuelos nuevoPlan = planesDeVuelosService.savePlanDeVuelo(request);
        return new ResponseEntity<>(nuevoPlan, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un plan de vuelo", description = "Modifica los datos de un plan de vuelo existente de acuerdo con su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Plan de vuelo actualizado exitosamente", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlanesDeVuelos.class))),
        @ApiResponse(responseCode = "400", description = "Existen errores de validación en los datos modificados", content = @Content),
        @ApiResponse(responseCode = "404", description = "Plan de vuelo no encontrado para actualizar", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlan(
            @Parameter(description = "ID del plan de vuelo que se desea actualizar", required = true, example = "1")
            @PathVariable Long id, 
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Estructura JSON con los nuevos campos del plan de vuelo", required = true)
            @Valid @RequestBody UpdatePlanRequest request, 
            BindingResult result
    ) {
        
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> 
                errores.put(error.getField(), error.getDefaultMessage())
            );
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }
        PlanesDeVuelos planActualizado = planesDeVuelosService.updatePlanDeVuelo(id, request);
        
        return new ResponseEntity<>(planActualizado, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar un plan de vuelo", description = "Elimina permanentemente un plan de vuelo del sistema mediante su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Plan de vuelo eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "No se puede eliminar. No existe el plan de vuelo con el ID proporcionado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlan(
        @Parameter(description = "ID del plan de vuelo que se desea eliminar", required = true, example = "1")
        @PathVariable Long id
    ) {
        String resultado = planesDeVuelosService.deletePlanDeVuelo(id);
        Map<String, String> respuestaJson = new HashMap<>();
        
        if (resultado == null) {
            respuestaJson.put("error", "No encontrado");
            respuestaJson.put("mensaje", "No se puede eliminar. No existe el plan de vuelo con el ID: " + id);
            
            return new ResponseEntity<>(respuestaJson, HttpStatus.NOT_FOUND); 
        }
        respuestaJson.put("mensaje", resultado); 
        return new ResponseEntity<>(respuestaJson, HttpStatus.OK); 
    }

    @Operation(summary = "Obtener planes de vuelo por piloto", description = "Lista todos los planes de vuelo registrados para un piloto a través de su RUN")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Planes de vuelo del piloto obtenidos con éxito", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlanesDeVuelos.class))),
        @ApiResponse(responseCode = "404", description = "No hay planes de vuelo registrados para el RUN consultado")
    })
    @GetMapping("/piloto/{runPiloto}")
    public ResponseEntity<?> getPlanesPorPiloto(
        @Parameter(description = "RUN del piloto a consultar", required = true, example = "12345678-9")
        @PathVariable String runPiloto
    ) {
        List<PlanesDeVuelos> planes = planesDeVuelosService.getPlanesPorPiloto(runPiloto);
        
        if (planes.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No encontrado");
            error.put("mensaje", "No hay planes de vuelo registrados para el piloto con RUN: " + runPiloto);
            
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); 
        }

        return new ResponseEntity<>(planes, HttpStatus.OK); 
    }

    @Operation(summary = "Obtener planes de vuelo por dron", description = "Lista todos los planes de vuelo asignados a un dron mediante su patente única")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Planes de vuelo del dron devueltos con éxito", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlanesDeVuelos.class))),
        @ApiResponse(responseCode = "404", description = "No hay planes de vuelo asociados a la patente provista")
    })
    @GetMapping("/dron/{patenteDron}")
    public ResponseEntity<?> getPlanesPorDron(
        @Parameter(description = "Patente única del dron a consultar", required = true, example = "DRON-99X-CL")
        @PathVariable String patenteDron
    ) {
        List<PlanesDeVuelos> planes = planesDeVuelosService.getPlanesPorDron(patenteDron);
        
        if (planes.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No encontrado");
            error.put("mensaje", "No hay planes de vuelo registrados para el dron patente: " + patenteDron);
            
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); 
        }

        return new ResponseEntity<>(planes, HttpStatus.OK); 
    }

    @Operation(summary = "Obtener total de planes de vuelo", description = "Retorna la cantidad total de registros de planes de vuelo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conteo obtenido con éxito", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class)))
    })
    @GetMapping("/total")
    public ResponseEntity<Integer> getTotalPlanes() {
        return new ResponseEntity<>(planesDeVuelosService.totalPlanesDeVuelos(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener planes de vuelo por contratista", description = "Filtra y lista todos los planes de vuelo asignados a un contratista específico por su RUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Planes de vuelo del contratista devueltos con éxito", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlanesDeVuelos.class))),
        @ApiResponse(responseCode = "404", description = "No hay planes de vuelo registrados para el contratista consultado")
    })
    @GetMapping("/contratista/{rutContratista}")
    public ResponseEntity<?> getPlanesPorContratista(
        @Parameter(description = "RUT de la empresa o contratista", required = true, example = "76123456-K")
        @PathVariable String rutContratista
    ) {
        List<PlanesDeVuelos> planes = planesDeVuelosService.getPlanesPorContratista(rutContratista);
        
        if (planes.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No encontrado");
            error.put("mensaje", "No hay planes de vuelo para el contratista con RUT: " + rutContratista);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(planes, HttpStatus.OK);
    }
}