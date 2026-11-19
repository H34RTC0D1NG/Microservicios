package com.Japon.Mensajeria.Modelo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.Data;

@Data 
@Entity 
@Table(name = "Contacto")
public class Mensajeria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("ID")
    private Long ID;

    @Column(name = "Nombre_Completo", nullable = false, length = 150)
    private String Nombre_Completo;

    @Column(nullable = false, length = 150)
    private String Correo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String Mensaje;

    @Column(name = "Estado", nullable = false, length = 20)
    private String Estado = "Pendiente";

    @Column(name = "Fecha_Creacion", nullable = false, updatable = false)
    @JsonProperty("Fecha_Creacion")
    private LocalDateTime Fecha_Creacion;

    @Column(name = "Respuesta", columnDefinition = "TEXT")
    private String Respuesta;

    @Column(name = "Fecha_Respuesta")
    private LocalDateTime Fecha_Respuesta;

    @PrePersist 
    protected void AsignarFechaCreacion() {
        this.Fecha_Creacion = LocalDateTime.now();
    }

}