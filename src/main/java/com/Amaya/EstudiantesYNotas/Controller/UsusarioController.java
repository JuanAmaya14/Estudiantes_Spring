package com.Amaya.EstudiantesYNotas.Controller;

import com.Amaya.EstudiantesYNotas.domain.Usuario.Datos.DatosListadoUsuario;
import com.Amaya.EstudiantesYNotas.domain.Usuario.Datos.DatosModificarUsuario;
import com.Amaya.EstudiantesYNotas.domain.Usuario.Datos.DatosRegistroUsuario;
import com.Amaya.EstudiantesYNotas.domain.Usuario.Datos.DatosRespuestaUsuario;
import com.Amaya.EstudiantesYNotas.domain.Usuario.Usuario;
import com.Amaya.EstudiantesYNotas.repositorio.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuario")
public class UsusarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registro")
    @Transactional
    public ResponseEntity RegistrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario,
                                           UriComponentsBuilder uriComponentsBuilder) {

        String contrasenha = passwordEncoder.encode(datosRegistroUsuario.contrasenha());

        Usuario usuario = usuarioRepository.save(new Usuario(datosRegistroUsuario, contrasenha));

        DatosRespuestaUsuario datosRespuestaUsuario = new DatosRespuestaUsuario(usuario.getId(), usuario.getNombre(),
                usuario.getContrasenha());

        URI uri = uriComponentsBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(uri).body(datosRespuestaUsuario);

    }

    @GetMapping
    public ResponseEntity ListarUsuarios() {

        return ResponseEntity.ok(usuarioRepository.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoUsuario> ListarUsuarioPorId(@PathVariable long id) {

        Usuario usuario = usuarioRepository.getReferenceById(id);

        DatosListadoUsuario datosListadoUsuario = new DatosListadoUsuario(usuario.getNombre(),
                usuario.getContrasenha());

        return ResponseEntity.ok(datosListadoUsuario);

    }

    @PutMapping
    @Transactional
    public ResponseEntity modificarUsuario(@RequestBody @Valid DatosModificarUsuario datosModificarUsuario) {

        String contrasenha = passwordEncoder.encode(datosModificarUsuario.contrasenha());

        Usuario usuario = usuarioRepository.getReferenceById(datosModificarUsuario.id());

        usuario.modificarUsuario(datosModificarUsuario, contrasenha);

        DatosRespuestaUsuario datosRespuestaUsuario = new DatosRespuestaUsuario(usuario.getId(), usuario.getNombre(),
                usuario.getContrasenha());

        return ResponseEntity.ok(datosRespuestaUsuario);

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarUsuario(@PathVariable long id) {

        usuarioRepository.deleteById(id);

        return ResponseEntity.ok("El usuario con el id: " + id + " fue eliminado exitosamente");

    }

}
