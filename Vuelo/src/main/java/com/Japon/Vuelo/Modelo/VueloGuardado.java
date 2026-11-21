package com.Japon.Vuelo.Modelo;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data; 

@Data 
@Entity
@Table(name = "Vuelo_Guardado")
public class VueloGuardado {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "Usuario_ID", nullable = false)
    private Long Usuario_ID;

    @Column(name = "Pasaje_ID", nullable = false)
    private Integer Pasaje_ID;

    @Column(name = "Ciudad", nullable = false, length = 80)
    private String Ciudad;

    @Column(name = "Kanji", nullable = false, length = 20)
    private String Kanji;

    @Column(name = "Codigo", nullable = false, length = 10)
    private String Codigo;

    @Column(name = "Vuelo", nullable = false, length = 20)
    private String Vuelo;

    @Column(name = "Fecha", nullable = false, length = 20)
    private String Fecha;

    @Column(name = "Hora", nullable = false, length = 10)
    private String Hora;

    @Column(name = "Puerta", nullable = false, length = 10)
    private String Puerta;

    @Column(name = "Asiento", nullable = false, length = 10)
    private String Asiento;

    @Column(name = "Clase", nullable = false, length = 30)
    private String Clase;

    @Column(name = "Precio", nullable = false, length = 30)
    private String Precio;

    @Column(name = "Color", nullable = false, length = 20)
    private String Color;

    @Column(name = "Fecha_Guardado", nullable = false, updatable = false)
    private LocalDateTime Fecha_Guardado;

    @PrePersist 
    protected void AsignarFecha() {
        this.Fecha_Guardado = LocalDateTime.now();
    }

}