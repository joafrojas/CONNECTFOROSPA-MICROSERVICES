package com.example.HistorialUsuariosConnectForoSpa.webclient;

import com.example.HistorialUsuariosConnectForoSpa.webclient.dto.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UsuarioClient {

    private final WebClient webClient;

    public UsuarioClient(@Value("${servicio.usuarios.url}") String urlBase) {
        this.webClient = WebClient.builder()
                .baseUrl(urlBase)
                .build();
    }

    public Mono<Usuario> obtenerUsuarioPorId(Long id) {
        return webClient.get()
                .uri("/" + id) 
                .retrieve()
                .bodyToMono(Usuario.class)
                .onErrorResume(error -> {
                    System.err.println("Error al conectar con el servicio de registro de usuarios: " + error.getMessage());
                    return Mono.empty();
                });
    }
}

