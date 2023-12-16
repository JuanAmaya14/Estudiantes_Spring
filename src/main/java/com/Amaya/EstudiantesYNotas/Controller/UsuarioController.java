package com.Amaya.EstudiantesYNotas.Controller;

import com.Amaya.EstudiantesYNotas.domain.Usuario.Datos.DatosListadoUsuario;
import com.Amaya.EstudiantesYNotas.domain.Usuario.Datos.DatosModificarUsuario;
import com.Amaya.EstudiantesYNotas.domain.Usuario.Datos.DatosRegistroUsuario;
import com.Amaya.EstudiantesYNotas.domain.Usuario.Datos.DatosRespuestaUsuario;
import com.Amaya.EstudiantesYNotas.domain.Usuario.Usuario;
import com.Amaya.EstudiantesYNotas.repositorio.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registro")
    @Transactional
    @Operation(summary = "Registra un usuario")
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
    @Operation(summary = "Lista todos los usuarios", security = @SecurityRequirement(name = "basicAuth"))
    public ResponseEntity<Page<DatosListadoUsuario>> ListarUsuarios(@PageableDefault(size = 20) Pageable pageable) {

        return ResponseEntity.ok(usuarioRepository.findAll(pageable).map(DatosListadoUsuario::new));

    }

    @GetMapping("/{id}")
    @Operation(summary = "Lista usuario por id", security = @SecurityRequirement(name = "basicAuth"))
    public ResponseEntity<DatosListadoUsuario> ListarUsuarioPorId(@PathVariable long id) {

        Usuario usuario = usuarioRepository.getReferenceById(id);

        DatosListadoUsuario datosListadoUsuario = new DatosListadoUsuario(usuario.getId(), usuario.getNombre(),
                usuario.getContrasenha());

        return ResponseEntity.ok(datosListadoUsuario);

    }

    @PutMapping
    @Transactional
    @Operation(summary = "Modifica usuario", security = @SecurityRequirement(name = "basicAuth"))
    public ResponseEntity modificarUsuario(@RequestBody @Valid DatosModificarUsuario datosModificarUsuario) {

        String contrasenha = passwordEncoder.encode(datosModificarUsuario.contrasenha());

        Usuario usuario = usuarioRepository.getReferenceById(datosModificarUsuario.id());

        long idUsuario = UsuarioLogeado();

        if (idUsuario == datosModificarUsuario.id()) {

            usuario.modificarUsuario(datosModificarUsuario, contrasenha);

            DatosRespuestaUsuario datosRespuestaUsuario = new DatosRespuestaUsuario(usuario.getId(), usuario.getNombre(),
                    usuario.getContrasenha());

            return ResponseEntity.ok(datosRespuestaUsuario);

        } else {

            return ResponseEntity.badRequest().body("No puedes modificar los datos de alguien más");

        }

    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina usuairo por id", security = @SecurityRequirement(name = "basicAuth"))
    public ResponseEntity eliminarUsuario(@PathVariable long id) {

        Usuario usuario = usuarioRepository.getReferenceById(id);

        long idUsuario = UsuarioLogeado();

        if (id == idUsuario) {

            usuarioRepository.deleteById(id);

            return ResponseEntity.ok("El usuario: " + usuario.getNombre() + " fue eliminado exitosamente");

        } else {

            return ResponseEntity.badRequest().body("No puedes borrar la cuenta de alguien más");

        }

    }

    public long UsuarioLogeado(){

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        UserDetails userDetail = (UserDetails) auth.getPrincipal();

        return usuarioRepository.getIdUsuarioByCorreo(userDetail.getUsername());

    }

}
