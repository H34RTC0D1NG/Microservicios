package com.Japon.Auth.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import com.Japon.Auth.Modelo.Usuario;
import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository <Usuario, Long> {

    @Query("Select U From Usuario U Where Lower(U.Correo) = Lower(:Correo)")
    Optional <Usuario> BuscarPorCorreo(@Param("Correo") String Correo);

}