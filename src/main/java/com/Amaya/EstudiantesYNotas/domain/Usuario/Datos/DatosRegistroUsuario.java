package com.Amaya.EstudiantesYNotas.domain.Usuario.Datos;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistroUsuario(

        @NotBlank
        String nombre,

        @NotBlank
        String contrasenha
) {
}
