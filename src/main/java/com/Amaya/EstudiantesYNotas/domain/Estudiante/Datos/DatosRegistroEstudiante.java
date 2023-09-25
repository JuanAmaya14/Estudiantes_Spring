package com.Amaya.EstudiantesYNotas.domain.Estudiante.Datos;

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
        double nota1,

        @NotNull
        double nota2,

        @NotNull
        double nota3


) {
}
