package com.edutech.evaluaciones.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;
    private String tipo; // por ejemplo: prueba, control, quiz
    private int puntajeMaximo;

    private Long contenidoId;  // Relación al contenido
}
