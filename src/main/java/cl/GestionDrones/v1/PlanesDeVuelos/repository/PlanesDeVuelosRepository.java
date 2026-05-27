package cl.GestionDrones.v1.PlanesDeVuelos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.GestionDrones.v1.PlanesDeVuelos.model.PlanesDeVuelos;


@Repository
public interface PlanesDeVuelosRepository extends JpaRepository<PlanesDeVuelos, Long> {
    

    List<PlanesDeVuelos> findByRunPiloto(String runPiloto);
    
    List<PlanesDeVuelos> findByPatenteDron(String patenteDron);
}
