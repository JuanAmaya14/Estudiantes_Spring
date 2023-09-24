package com.Amaya.EstudiantesYNotas.domain.datos;

import jakarta.validation.constraints.NotNull;

public record DatosModificarEstudiante(

        @NotNull
        long id,
        String nombre,
        String apellido,
        String descripcion,
        double nota1,
        double nota2,
        double nota3) {
}
