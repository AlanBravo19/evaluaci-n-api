package com.edutech.evaluaciones.service;

import com.edutech.evaluaciones.client.ContenidoClient;
import com.edutech.evaluaciones.dto.ContenidoDTO;
import com.edutech.evaluaciones.model.Evaluacion;
import com.edutech.evaluaciones.repository.EvaluacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EvaluacionServiceTest {

    @Mock
    private EvaluacionRepository evaluacionRepository; //simula la base de datos 

    @Mock
    private ContenidoClient contenidoClient; //llama al microservicio de contenido

    @InjectMocks
    private EvaluacionService evaluacionService; // crea instancia de evaluacionservice e inyecta los mocks anteriores/como sus dependencias

    private Evaluacion evaluacion;

    @BeforeEach
    void setUp() {
        evaluacion = new Evaluacion();
        evaluacion.setId(1L);
        evaluacion.setTitulo("Evaluacion Test");
        evaluacion.setContenidoId(99L);
        evaluacion.setPuntajeMaximo(100);
    }

    @Test
    void testGuardarEvaluacion() {
        when(evaluacionRepository.save(any(Evaluacion.class))).thenReturn(evaluacion);

        Evaluacion guardada = evaluacionService.guardarEvaluacion(evaluacion);

        assertNotNull(guardada);
        assertEquals("Evaluacion Test", guardada.getTitulo());
        verify(evaluacionRepository, times(1)).save(any(Evaluacion.class));
    }

    @Test
    void testObtenerTodas() {
        when(evaluacionRepository.findAll()).thenReturn(List.of(evaluacion));

        List<Evaluacion> lista = evaluacionService.obtenerTodas();

        assertFalse(lista.isEmpty());
        assertEquals(1, lista.size());
        verify(evaluacionRepository, times(1)).findAll();
    }

    @Test
    void testObtenerContenidoDeEvaluacion() {
        ContenidoDTO contenido = new ContenidoDTO();
        contenido.setId(99L);
        contenido.setTitulo("Contenido Test");

        when(evaluacionRepository.findById(1L)).thenReturn(Optional.of(evaluacion));
        when(contenidoClient.obtenerContenidoPorId(99L)).thenReturn(contenido);

        ContenidoDTO resultado = evaluacionService.obtenerContenidoDeEvaluacion(1L);

        assertNotNull(resultado);
        assertEquals("Contenido Test", resultado.getTitulo());
        verify(contenidoClient, times(1)).obtenerContenidoPorId(99L);
    }

    @Test
    void testObtenerContenidoDeEvaluacionNoExiste() {
        // Caso donde no existe la evaluación con ese ID
        when(evaluacionRepository.findById(999L)).thenReturn(Optional.empty());

        ContenidoDTO resultado = evaluacionService.obtenerContenidoDeEvaluacion(999L);

        assertNull(resultado); // Ya que el método retorna null si no encuentra
        verify(evaluacionRepository, times(1)).findById(999L);
        verify(contenidoClient, never()).obtenerContenidoPorId(any());
    }

    @Test
    void testActualizarEvaluacion() {
        Evaluacion nueva = new Evaluacion();
        nueva.setTitulo("Actualizado");
        nueva.setDescripcion("Desc");
        nueva.setTipo("quiz");
        nueva.setPuntajeMaximo(90);

        when(evaluacionRepository.findById(1L)).thenReturn(Optional.of(evaluacion));
        when(evaluacionRepository.save(any(Evaluacion.class))).thenReturn(evaluacion);

        Evaluacion actualizada = evaluacionService.actualizarEvaluacion(1L, nueva);

        assertNotNull(actualizada);
        assertEquals("Actualizado", actualizada.getTitulo());
        verify(evaluacionRepository, times(1)).save(any(Evaluacion.class));
    }

    @Test
    void testActualizarEvaluacionNoExiste() {
        Evaluacion nueva = new Evaluacion();
        nueva.setTitulo("Nueva");

        when(evaluacionRepository.findById(99L)).thenReturn(Optional.empty());

        Evaluacion actualizada = evaluacionService.actualizarEvaluacion(99L, nueva);

        assertNull(actualizada); // porque retorna null si no encuentra la evaluación
        verify(evaluacionRepository, never()).save(any(Evaluacion.class));
    }

    @Test
    void testEliminarEvaluacion() {
        doNothing().when(evaluacionRepository).deleteById(1L);

        evaluacionService.eliminarEvaluacion(1L);

        verify(evaluacionRepository, times(1)).deleteById(1L);
    }
}
