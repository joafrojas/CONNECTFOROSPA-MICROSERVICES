package com.example.HistorialUsuariosConnectForoSpa.webclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialUsuarioResponse {
    private Long id_historial;
    private String accion;
    private LocalDateTime fecha_accion;
    private Long id_usuario;
    private String nombreUsuario;
}

