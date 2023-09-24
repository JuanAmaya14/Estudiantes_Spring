package com.Amaya.EstudiantesYNotas.domain.datos;

public record DatosRespuestaEstudiante(
        long id,
        String nombre,
        String apellido,
        String descripcion,
        double nota1,
        double nota2,
        double nota3,
        double notaFinal) {
}
