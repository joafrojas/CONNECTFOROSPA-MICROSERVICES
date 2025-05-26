package com.example.ReportesyModeracionConnectForoSpa.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_reporte;
    
    private Long id_usuario; //FK que va hacia microservicio de usuarios

    private String motivo;

    private String estado;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fecha_reporte; 

}
