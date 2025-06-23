package com.edutech.evaluaciones.dto;

import java.time.LocalDate;

public class HistorialDTO {
    private Long id;
    private Long estudianteId;
    private String curso;
    private String evaluacion;
    private Double nota;
    private LocalDate fecha;

    // Constructor vacío
    public HistorialDTO() {}

    // Getters y Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getEstudianteId() {
        return estudianteId;
    }
    public void setEstudianteId(Long estudianteId) {
        this.estudianteId = estudianteId;
    }

    public String getCurso() {
        return curso;
    }
    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getEvaluacion() {
        return evaluacion;
    }
    public void setEvaluacion(String evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Double getNota() {
        return nota;
    }
    public void setNota(Double nota) {
        this.nota = nota;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}