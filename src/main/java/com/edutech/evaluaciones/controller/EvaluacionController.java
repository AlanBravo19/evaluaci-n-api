package com.edutech.evaluaciones.controller;

import com.edutech.evaluaciones.model.Evaluacion;
import com.edutech.evaluaciones.service.EvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.edutech.evaluaciones.dto.ContenidoDTO;


import java.util.List;

@RestController
@RequestMapping("/api/evaluaciones")
@CrossOrigin(origins = "*")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    @GetMapping
    public List<Evaluacion> listarEvaluaciones() {
        return evaluacionService.obtenerTodas();
    }

    @PostMapping
    public Evaluacion crearEvaluacion(@RequestBody Evaluacion evaluacion) {
        return evaluacionService.guardarEvaluacion(evaluacion);
    }

    @GetMapping("/{id}/contenido")
public ContenidoDTO obtenerContenidoDeEvaluacion(@PathVariable Long id) {
    return evaluacionService.obtenerContenidoDeEvaluacion(id);
}


}

