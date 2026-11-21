package com.Japon.Vuelo.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import com.Japon.Vuelo.Modelo.VueloGuardado;
import java.util.Optional;
import java.util.List;

public interface VueloRepositorio extends JpaRepository <VueloGuardado, Long> {

    @Query("Select V From VueloGuadado V Where V.Usuario_ID = :Usuario Order By V.Fecha_Guardado Desc")
    List <VueloGuardado> BuscarPorUsuario(@Param("Usuario") Long Usuario);

    @Query("Select V From VueloGuardado V Where V.Usuario_ID = :Usuario And V.Pasaje_ID = :Pasaje")
    Optional <VueloGuardado> BuscarUno(@Param("Usuario") Long Usuario, @Param("Pasaje") Integer Pasaje);

}