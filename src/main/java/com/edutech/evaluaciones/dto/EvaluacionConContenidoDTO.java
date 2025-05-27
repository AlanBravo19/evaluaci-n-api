package com.edutech.evaluaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                 
@NoArgsConstructor    
@AllArgsConstructor   
public class EvaluacionConContenidoDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String tipo;
    private Integer puntajeMaximo;
    private ContenidoDTO contenido;  // asumiendo que ContenidoDTO ya lo tienes creado
}
