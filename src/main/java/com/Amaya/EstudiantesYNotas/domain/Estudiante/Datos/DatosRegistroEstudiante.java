package com.Amaya.EstudiantesYNotas.domain.Estudiante.Datos;

import com.Amaya.EstudiantesYNotas.domain.Estudiante.Curso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroEstudiante(

        @NotBlank
        String nombre,

        @NotBlank
        String apellido,

        @NotBlank
        String descripcion,

        @NotNull
        Curso curso,

        @NotNull
        double nota1,

        @NotNull
        double nota2,

        @NotNull
        double nota3

) {
}
