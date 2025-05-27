package com.edutech.evaluaciones.client;

import com.edutech.evaluaciones.dto.ContenidoDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ContenidoClient {

    private final RestTemplate restTemplate;

    // URL base del microservicio contenido, ajusta el puerto si cambiaste algo
    private final String baseUrl = "http://localhost:8081/api/contenidos";

    public ContenidoClient() {
        this.restTemplate = new RestTemplate();
    }

    public ContenidoDTO obtenerContenidoPorId(Long id) {
        String url = baseUrl + "/" + id;
        return restTemplate.getForObject(url, ContenidoDTO.class);
    }
}