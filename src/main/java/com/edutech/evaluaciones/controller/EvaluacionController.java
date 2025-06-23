package com.edutech.evaluaciones.controller;

import com.edutech.evaluaciones.model.Evaluacion;
import com.edutech.evaluaciones.service.EvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.edutech.evaluaciones.dto.ContenidoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;



import java.util.List;

@RestController
@RequestMapping("/api/evaluaciones")
@CrossOrigin(origins = "*")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

@GetMapping
public CollectionModel<EntityModel<Evaluacion>> listarEvaluaciones() {
    List<Evaluacion> evaluaciones = evaluacionService.obtenerTodas();

    List<EntityModel<Evaluacion>> evaluacionesConLinks = evaluaciones.stream()
        .map(eva -> EntityModel.of(eva,
                linkTo(methodOn(EvaluacionController.class).obtenerContenidoDeEvaluacion(eva.getId())).withRel("contenido"),
                linkTo(methodOn(EvaluacionController.class).listarEvaluaciones()).withSelfRel()))
        .toList();

    return CollectionModel.of(evaluacionesConLinks,
            linkTo(methodOn(EvaluacionController.class).listarEvaluaciones()).withSelfRel());
}


    //@GetMapping
    //public List<Evaluacion> listarEvaluaciones() {
    //    return evaluacionService.obtenerTodas();
    //}

    @PostMapping
    public Evaluacion crearEvaluacion(@RequestBody Evaluacion evaluacion) {
        return evaluacionService.guardarEvaluacion(evaluacion);
    }


    @GetMapping("/{id}/contenido")
public EntityModel<ContenidoDTO> obtenerContenidoDeEvaluacion(@PathVariable Long id) {
    ContenidoDTO contenido = evaluacionService.obtenerContenidoDeEvaluacion(id);

    return EntityModel.of(contenido,
            linkTo(methodOn(EvaluacionController.class).obtenerContenidoDeEvaluacion(id)).withSelfRel(),
            linkTo(methodOn(EvaluacionController.class).listarEvaluaciones()).withRel("evaluaciones"));
}


    //@GetMapping("/{id}/contenido")
//public ContenidoDTO obtenerContenidoDeEvaluacion(@PathVariable Long id) {
  //  return evaluacionService.obtenerContenidoDeEvaluacion(id);
//}



}

