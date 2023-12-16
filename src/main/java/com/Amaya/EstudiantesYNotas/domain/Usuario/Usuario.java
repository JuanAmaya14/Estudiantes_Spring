package com.Amaya.EstudiantesYNotas.domain.Usuario;

import com.Amaya.EstudiantesYNotas.domain.Usuario.Datos.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ADMIN"));
    }

    @Override
    public String getPassword() {
        return contrasenha;
    }

    @Override
    public String getUsername() {
        return nombre;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
