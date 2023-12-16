package com.Amaya.EstudiantesYNotas.repositorio;

import com.Amaya.EstudiantesYNotas.domain.Usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByNombre(String username);

    @Query("""
            select u.id from Usuario u where u.nombre=:nombre
            """)
    long getIdUsuarioByCorreo(String nombre);
}
