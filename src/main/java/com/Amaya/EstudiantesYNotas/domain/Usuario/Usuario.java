package com.Amaya.EstudiantesYNotas.domain.Usuario;

import com.Amaya.EstudiantesYNotas.domain.Usuario.Datos.DatosModificarUsuario;
import com.Amaya.EstudiantesYNotas.domain.Usuario.Datos.DatosRegistroUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;

    private String contrasenha;


    public Usuario(DatosRegistroUsuario datosRegistroUsuario, String contrasenha) {

        this.nombre = datosRegistroUsuario.nombre();
        this.contrasenha = contrasenha;

    }

    public void modificarUsuario(DatosModificarUsuario datosModificarUsuario, String contrasenha) {

        if (datosModificarUsuario.nombre() != null) {

            this.nombre = datosModificarUsuario.nombre();
        }

        if (datosModificarUsuario.contrasenha() != null) {

            this.contrasenha = contrasenha;

        }
    }

}
