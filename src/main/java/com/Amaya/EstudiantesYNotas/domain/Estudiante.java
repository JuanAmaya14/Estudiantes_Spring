package com.Amaya.EstudiantesYNotas.domain;

import com.Amaya.EstudiantesYNotas.domain.datos.DatosModificarEstudiante;
import com.Amaya.EstudiantesYNotas.domain.datos.DatosRegistroEstudiante;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "estudiantes")
@Entity(name = "Estudiante")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String apellido;
    private String descripcion;
    private double nota1;
    private double nota2;
    private double nota3;
    private double notaFinal;

    public Estudiante(DatosRegistroEstudiante datosRegistroEstudiante) {

        this.nombre = datosRegistroEstudiante.nombre();
        this.apellido = datosRegistroEstudiante.apellido();
        this.descripcion = datosRegistroEstudiante.descripcion();
        this.nota1 = datosRegistroEstudiante.nota1();
        this.nota2 = datosRegistroEstudiante.nota2();
        this.nota3 = datosRegistroEstudiante.nota3();
        this.notaFinal = (nota1 + nota2 + nota3) / 3;

    }

    public void modificarEstudiante(DatosModificarEstudiante datosModificarEstudiante) {

        if(datosModificarEstudiante.nombre() != null){

            this.nombre = datosModificarEstudiante.nombre();

        }

        if (datosModificarEstudiante.apellido() != null){

            this.apellido = datosModificarEstudiante.apellido();

        }

        if (datosModificarEstudiante.descripcion() != null){

            this.descripcion = datosModificarEstudiante.descripcion();

        }

        if(datosModificarEstudiante.nota1() != 0){

            this.nota1 = datosModificarEstudiante.nota1();

        }

        if(datosModificarEstudiante.nota2() != 0){

            this.nota2 = datosModificarEstudiante.nota2();

        }

        if(datosModificarEstudiante.nota3() != 0){

            this.nota3 = datosModificarEstudiante.nota3();

        }

        this.notaFinal = (nota1 + nota2 + nota3) / 3;


    }
}
