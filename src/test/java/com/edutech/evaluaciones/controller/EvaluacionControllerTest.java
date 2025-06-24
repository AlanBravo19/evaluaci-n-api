package com.edutech.evaluaciones.controller;

import com.edutech.evaluaciones.dto.ContenidoDTO;
import com.edutech.evaluaciones.model.Evaluacion;
import com.edutech.evaluaciones.service.EvaluacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")//para usar el aplication properties test
@WebMvcTest(value = EvaluacionController.class, //le dice a spring que solo cargue el controlador 
        excludeAutoConfiguration = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})//desactiva la base de datos, ya que no es necesaria para pruebas unitarias
public class EvaluacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EvaluacionService evaluacionService;

    @Autowired
    private ObjectMapper objectMapper;

    private Evaluacion eva;

    @BeforeEach
    public void setUp() {
        eva = new Evaluacion();
        eva.setId(1L);
        eva.setTitulo("Control 1");
        eva.setPuntajeMaximo(100);
    }

    @Test
    public void testListarEvaluaciones() throws Exception {
        Mockito.when(evaluacionService.obtenerTodas()).thenReturn(List.of(eva));

        mockMvc.perform(get("/api/evaluaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.evaluacionList[0].titulo", is("Control 1")));

    }

    @Test
    public void testCrearEvaluacion() throws Exception {
        Evaluacion nuevaEva = new Evaluacion();
        nuevaEva.setId(2L);
        nuevaEva.setTitulo("Nuevo Examen");
        nuevaEva.setPuntajeMaximo(50);

        Mockito.when(evaluacionService.guardarEvaluacion(any(Evaluacion.class))).thenReturn(nuevaEva);

        String jsonEvaluacion = objectMapper.writeValueAsString(nuevaEva);

        mockMvc.perform(post("/api/evaluaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEvaluacion))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", is("Nuevo Examen")))
                .andExpect(jsonPath("$.puntajeMaximo", is(50)));
    }

    @Test
    public void testObtenerContenidoDeEvaluacion() throws Exception {
        ContenidoDTO contenido = new ContenidoDTO();
        contenido.setId(1L);
        contenido.setTitulo("Contenido Asociado");

        Mockito.when(evaluacionService.obtenerContenidoDeEvaluacion(1L)).thenReturn(contenido);

        mockMvc.perform(get("/api/evaluaciones/1/contenido"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", is("Contenido Asociado")));
    }


@Test
public void testActualizarEvaluacion() throws Exception {
    Evaluacion actualizada = new Evaluacion();
    actualizada.setId(1L);
    actualizada.setTitulo("Evaluación Actualizada");
    actualizada.setDescripcion("Descripción actualizada");
    actualizada.setTipo("examen");
    actualizada.setPuntajeMaximo(90);
    actualizada.setContenidoId(5L);

    Mockito.when(evaluacionService.actualizarEvaluacion(Mockito.eq(1L), any(Evaluacion.class)))
            .thenReturn(actualizada);

    String jsonActualizada = objectMapper.writeValueAsString(actualizada);

    mockMvc.perform(put("/api/evaluaciones/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonActualizada))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.titulo", is("Evaluación Actualizada")))
            .andExpect(jsonPath("$.puntajeMaximo", is(90)));
}

@Test
public void testEliminarEvaluacion() throws Exception {
    // No hace falta que el servicio retorne nada. Solo verificamos que responda OK.
    Mockito.doNothing().when(evaluacionService).eliminarEvaluacion(1L);

    mockMvc.perform(delete("/api/evaluaciones/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is("Evaluación eliminada correctamente")));
}

   
}
