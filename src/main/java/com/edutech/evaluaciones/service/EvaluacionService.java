package com.edutech.evaluaciones.service;

import com.edutech.evaluaciones.client.ContenidoClient;
import com.edutech.evaluaciones.dto.ContenidoDTO;
import com.edutech.evaluaciones.model.Evaluacion;
import com.edutech.evaluaciones.repository.EvaluacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluacionService {

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private ContenidoClient contenidoClient;

    // Guardar una nueva evaluación
    public Evaluacion guardarEvaluacion(Evaluacion evaluacion) {
        return evaluacionRepository.save(evaluacion);
    }

    // Obtener todas las evaluaciones
    public List<Evaluacion> obtenerTodas() {
        return evaluacionRepository.findAll();
    }

    // Obtener una evaluación por su ID
    public Optional<Evaluacion> obtenerPorId(Long id) {
        return evaluacionRepository.findById(id);
    }


    public ContenidoDTO obtenerContenidoPorId(Long contenidoId) {
    return contenidoClient.obtenerContenidoPorId(contenidoId);
}


    public ContenidoDTO obtenerContenidoDeEvaluacion(Long evaluacionId) {
    Optional<Evaluacion> opt = evaluacionRepository.findById(evaluacionId);
    if (opt.isPresent()) {
        Long contenidoId = opt.get().getContenidoId();
        return contenidoClient.obtenerContenidoPorId(contenidoId);
    }
    return null; // o lanzar excepción
}

    // Actualizar una evaluación
    public Evaluacion actualizarEvaluacion(Long id, Evaluacion nuevaEvaluacion) {
    return evaluacionRepository.findById(id).map(e -> {
        e.setTitulo(nuevaEvaluacion.getTitulo());
        e.setDescripcion(nuevaEvaluacion.getDescripcion());
        e.setTipo(nuevaEvaluacion.getTipo());
        e.setPuntajeMaximo(nuevaEvaluacion.getPuntajeMaximo());
        return evaluacionRepository.save(e);
    }).orElse(null);
}

    // Eliminar una evaluación por su ID
    public void eliminarEvaluacion(Long id) {
        evaluacionRepository.deleteById(id);
    }
}