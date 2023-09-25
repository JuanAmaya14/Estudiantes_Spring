package com.Amaya.EstudiantesYNotas.repositorio;

import com.Amaya.EstudiantesYNotas.domain.Estudiante.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    @Query("""
            select e.id from Estudiante e where e.nombre = :nombre and e.apellido = :apellido
            """)
    String encontrarIdPorNombreYApellidoEstudiante(String nombre, String apellido);
}
