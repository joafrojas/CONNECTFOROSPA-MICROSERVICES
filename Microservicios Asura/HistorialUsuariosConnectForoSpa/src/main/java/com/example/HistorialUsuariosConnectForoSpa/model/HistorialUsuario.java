package com.example.HistorialUsuariosConnectForoSpa.model;


import java.time.LocalDateTime;

import com.example.HistorialUsuariosConnectForoSpa.webclient.dto.Usuario;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_historial;

    private String accion;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fecha_accion = LocalDateTime.now();

    private Long id_usuario;


    @Transient
    private Usuario usuario; // lo usaremos solo como ayuda, no lo mapearemos


}
