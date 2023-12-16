package com.Amaya.EstudiantesYNotas.domain.Usuario.Datos;

import com.Amaya.EstudiantesYNotas.domain.Usuario.Usuario;

public record DatosListadoUsuario(long id, String nombre, String contrasenha) {

    public DatosListadoUsuario(Usuario usuario) {

        this(usuario.getId(), usuario.getNombre(), usuario.getContrasenha());

    }

}
