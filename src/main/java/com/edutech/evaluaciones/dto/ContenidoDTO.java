package com.edutech.evaluaciones.dto;

import lombok.Data;

@Data
public class ContenidoDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String tipo;
    private String url;
    // agregar otros campos que consideres

    // Constructor vacío (requerido para Jackson)
    public ContenidoDTO() {}

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}