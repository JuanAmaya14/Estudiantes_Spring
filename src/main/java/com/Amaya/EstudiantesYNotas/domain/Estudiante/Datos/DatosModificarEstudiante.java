package com.Amaya.EstudiantesYNotas.domain.Estudiante.Datos;

import jakarta.validation.constraints.NotNull;

public record DatosModificarEstudiante(

        @NotNull
        long id,
        String nombre,
        String apellido,
        String descripcion,
        String curso,
        double nota1,
        double nota2,
        double nota3) {
}
