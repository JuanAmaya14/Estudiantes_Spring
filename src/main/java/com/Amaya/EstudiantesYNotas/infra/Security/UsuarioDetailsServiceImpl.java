package com.Amaya.EstudiantesYNotas.infra.Security;

import com.Amaya.EstudiantesYNotas.domain.Usuario.Usuario;
import com.Amaya.EstudiantesYNotas.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombre(username);

        if (usuario != null) {
            return new User(usuario.getNombre(), usuario.getContrasenha(), usuario.getAuthorities());
        } else {
            throw new UsernameNotFoundException("El usuario: " + usuario + " no existe");
        }
    }
}
