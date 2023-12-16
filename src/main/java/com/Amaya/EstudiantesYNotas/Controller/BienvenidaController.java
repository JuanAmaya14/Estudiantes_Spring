package com.Amaya.EstudiantesYNotas.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Tag(name = "Bienvenida")
public class BienvenidaController {

    @GetMapping
    public ResponseEntity bienvenida() {

        return ResponseEntity.ok("<h1> Bienvenido al sistema </h1>");

    }

}
