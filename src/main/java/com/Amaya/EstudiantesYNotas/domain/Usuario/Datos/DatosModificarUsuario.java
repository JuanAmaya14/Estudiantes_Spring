package com.Amaya.EstudiantesYNotas.domain.Usuario.Datos;

import jakarta.validation.constraints.NotNull;

public record DatosModificarUsuario(

        @NotNull
        long id,

        String nombre,

        String contrasenha
) {
}
