package com.Amaya.EstudiantesYNotas.Controller;

import com.Amaya.EstudiantesYNotas.domain.Estudiante.Datos.DatosListadoEstudiante;
import com.Amaya.EstudiantesYNotas.domain.Estudiante.Datos.DatosModificarEstudiante;
import com.Amaya.EstudiantesYNotas.domain.Estudiante.Datos.DatosRegistroEstudiante;
import com.Amaya.EstudiantesYNotas.domain.Estudiante.Datos.DatosRespuestaEstudiante;
import com.Amaya.EstudiantesYNotas.domain.Estudiante.Estudiante;
import com.Amaya.EstudiantesYNotas.repositorio.EstudianteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.DecimalFormat;
import java.util.List;

@RestController
@RequestMapping("/estudiante")
@Tag(name = "Estudiante")
@SecurityRequirement(name = "basicAuth")
public class EstudianteController {

    @Autowired
    EstudianteRepository estudianteRepository;

    @PostMapping
    @Transactional
    @Operation(summary = "Registra un estudiante")
    public ResponseEntity registrarEstudiante(@RequestBody @Valid DatosRegistroEstudiante datosRegistroEstudiante,
                                              UriComponentsBuilder uriComponentsBuilder) {

        String idEstudiante = estudianteRepository.encontrarIdPorNombreYApellidoEstudiante(datosRegistroEstudiante.nombre(),
                datosRegistroEstudiante.apellido());

        if (idEstudiante == null) {

            if (datosRegistroEstudiante.nota1() <= 0 || datosRegistroEstudiante.nota1() >= 6) {

                return ResponseEntity.badRequest().body("la nota 1 debe ser un numero entre 1 y 5");

            } else if (datosRegistroEstudiante.nota2() <= 0 || datosRegistroEstudiante.nota2() >= 6) {

                return ResponseEntity.badRequest().body("la nota 2 debe ser un numero entre 1 y 5");

            } else if (datosRegistroEstudiante.nota3() <= 0 || datosRegistroEstudiante.nota3() >= 6) {

                return ResponseEntity.badRequest().body("la nota 3 debe ser un numero entre 1 y 5");

            } else {

                Estudiante estudiante = estudianteRepository.save(new Estudiante(datosRegistroEstudiante));

                DatosRespuestaEstudiante datosRespuestaEstudiante = new DatosRespuestaEstudiante(estudiante.getId(),
                        estudiante.getNombre(), estudiante.getApellido(), estudiante.getDescripcion(),
                        estudiante.getCurso().toString(), estudiante.getNota1(), estudiante.getNota2(),
                        estudiante.getNota3(), estudiante.getNotaFinal());

                URI uri = uriComponentsBuilder.path("/estudiante/{id}").buildAndExpand(estudiante.getId()).toUri();

                return ResponseEntity.created(uri).body(datosRespuestaEstudiante);

            }

        } else {

            return ResponseEntity.badRequest().body("Ya hay un estudiante con ese nombre y ese apellido");

        }

    }

    @GetMapping
    @Operation(summary = "Lista todos los estudiantes", security = @SecurityRequirement(name = "basicAuth"))
    public List<Estudiante> listarEstudiantes() {

        return estudianteRepository.findAll();

    }

    @GetMapping("/{id}")
    @Operation(summary = "Lista estudiante por id")
    public ResponseEntity<DatosListadoEstudiante> listarEstudiantesPorId(@PathVariable long id) {

        Estudiante estudiante = estudianteRepository.getReferenceById(id);

        DecimalFormat df = new DecimalFormat("0.0");

        String notaFinal = df.format(estudiante.getNotaFinal());

        DatosListadoEstudiante datosListadoEstudiante = new DatosListadoEstudiante(estudiante.getNombre(),
                estudiante.getApellido(), estudiante.getDescripcion(), estudiante.getCurso().toString(),
                estudiante.getNota1(), estudiante.getNota2(), estudiante.getNota3(), notaFinal);

        return ResponseEntity.ok(datosListadoEstudiante);

    }

    @PutMapping
    @Transactional
    @Operation(summary = "Modificar estudiante")
    public ResponseEntity modificarEstudiante(@RequestBody @Valid DatosModificarEstudiante datosModificarEstudiante) {

        Estudiante estudiante = estudianteRepository.getReferenceById(datosModificarEstudiante.id());

        String idEstudiante = estudianteRepository.encontrarIdPorNombreYApellidoEstudiante(datosModificarEstudiante.nombre(),
                datosModificarEstudiante.apellido());

        if (idEstudiante == null) {

            if (datosModificarEstudiante.nota1() != 0 &&
                    datosModificarEstudiante.nota1() <= 0 || datosModificarEstudiante.nota1() >= 6) {

                return ResponseEntity.badRequest().body("la nota 1 debe ser un numero entre 1 y 5");

            } else if (datosModificarEstudiante.nota2() != 0 &&
                    datosModificarEstudiante.nota2() <= 0 || datosModificarEstudiante.nota2() >= 6) {

                return ResponseEntity.badRequest().body("la nota 2 debe ser un numero entre 1 y 5");

            } else if (datosModificarEstudiante.nota3() != 0 &&
                    datosModificarEstudiante.nota3() <= 0 || datosModificarEstudiante.nota3() >= 6) {

                return ResponseEntity.badRequest().body("la nota 3 debe ser un numero entre 1 y 5");

            } else {

                estudiante.modificarEstudiante(datosModificarEstudiante);

                DatosRespuestaEstudiante datosRespuestaEstudiante = new DatosRespuestaEstudiante(estudiante.getId(),
                        estudiante.getNombre(), estudiante.getApellido(), estudiante.getDescripcion(),
                        estudiante.getCurso().toString(), estudiante.getNota1(), estudiante.getNota2(),
                        estudiante.getNota3(), estudiante.getNotaFinal());

                return ResponseEntity.ok(datosRespuestaEstudiante);

            }

        } else {

            return ResponseEntity.badRequest().body("Ya hay un estudiante con ese nombre y ese apellido");

        }


    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina un estudiante")
    public ResponseEntity eliminarEstudiante(@PathVariable long id) {

        Estudiante estudiante = estudianteRepository.getReferenceById(id);

        estudianteRepository.deleteById(id);

        return ResponseEntity.ok("El estudiante con el id: " + estudiante.getNombre() + " " + estudiante.getApellido() +
                " fue eliminado exitosamente");

    }


}
