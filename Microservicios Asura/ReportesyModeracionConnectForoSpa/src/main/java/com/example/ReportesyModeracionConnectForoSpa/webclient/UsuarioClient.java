package com.example.ReportesyModeracionConnectForoSpa.webclient;

import com.example.ReportesyModeracionConnectForoSpa.webclient.dto.Usuario;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UsuarioClient {

    private final WebClient webClient;

    public UsuarioClient(WebClient.Builder builder){
        this.webClient = builder
                .baseUrl("http://localhost:8083/api/usuarios")
                .build();
    }

    public Mono<Usuario> obtenerUsuarioPorId(Long id){
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Usuario.class);
                    
    }


}
