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

@RestController
@RequestMapping("/api/planes-de-vuelos")
public class PLanesDeVuelosController {

    @Autowired
    private PlanesDeVuelosService planesDeVuelosService;


    @GetMapping
    public ResponseEntity<List<PlanesDeVuelos>> getAllPlanes() {
        return new ResponseEntity<>(planesDeVuelosService.getPlanesDeVuelos(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getPlanById(@PathVariable Long id) {
        PlanesDeVuelos plan = planesDeVuelosService.getPlanDeVueloId(id);
        
        if (plan == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No encontrado");
            error.put("mensaje", "No existe el plan de vuelo con el ID: " + id);
            
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // Retorna 404
        }
        return new ResponseEntity<>(plan, HttpStatus.OK); // Retorna 200
    }


    @PostMapping
    public ResponseEntity<?> createPlan(@Valid @RequestBody CreatePlanRequest request, BindingResult result) {
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

   
    @PutMapping
    public ResponseEntity<?> updatePlan(@Valid @RequestBody UpdatePlanRequest request, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> 
                errores.put(error.getField(), error.getDefaultMessage())
            );
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        PlanesDeVuelos planActualizado = planesDeVuelosService.updatePlanDeVuelo(request);
        return new ResponseEntity<>(planActualizado, HttpStatus.OK);
    }

   

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlan(@PathVariable Long id) {
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




    @GetMapping("/piloto/{runPiloto}")
    public ResponseEntity<?> getPlanesPorPiloto(@PathVariable String runPiloto) {
        List<PlanesDeVuelos> planes = planesDeVuelosService.getPlanesPorPiloto(runPiloto);
        
        if (planes.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No encontrado");
            error.put("mensaje", "No hay planes de vuelo registrados para el piloto con RUN: " + runPiloto);
            
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); 
        }

        return new ResponseEntity<>(planes, HttpStatus.OK); 
    }


    @GetMapping("/dron/{patenteDron}")
    public ResponseEntity<?> getPlanesPorDron(@PathVariable String patenteDron) {
        List<PlanesDeVuelos> planes = planesDeVuelosService.getPlanesPorDron(patenteDron);
        
        if (planes.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No encontrado");
            error.put("mensaje", "No hay planes de vuelo registrados para el dron patente: " + patenteDron);
            
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); 
        }

        return new ResponseEntity<>(planes, HttpStatus.OK); 
    }


    @GetMapping("/total")
    public ResponseEntity<Integer> getTotalPlanes() {
        return new ResponseEntity<>(planesDeVuelosService.totalPlanesDeVuelos(), HttpStatus.OK);
    }
}