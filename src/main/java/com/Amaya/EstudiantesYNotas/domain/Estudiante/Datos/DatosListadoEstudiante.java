package com.Amaya.EstudiantesYNotas.domain.Estudiante.Datos;

public record DatosListadoEstudiante(
        String nombre,
        String apellido,
        String descripcion,
        String curso,
        double nota1,
        double nota2,
        double nota3,
        String notaFinal) {
}
