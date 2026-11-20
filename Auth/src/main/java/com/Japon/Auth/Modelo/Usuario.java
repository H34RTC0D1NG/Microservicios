package com.Japon.Auth.Modelo;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Data 
@Entity
@Table(name = "Usuarios")
public class Usuario {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "Nombre_Completo", nullable = false, length = 150)
    private String Nombre_Completo;

    @Column(name = "Correo", nullable = false, length = 150, unique = true)
    private String Correo;

    @Column(name = "Contraseña", nullable = false, length = 255)
    private String Contraseña;

    @Column(name = "Rol", nullable = false, length = 20)
    private String Rol = "Usuario";

    @Column(name = "Fecha_Registro", nullable = false, updatable = false) 
    private LocalDateTime Fecha_Registro;

    @PrePersist 
    protected void AsignarFechaRegistro() {
        this.Fecha_Registro = LocalDateTime.now();
    }

}