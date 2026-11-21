package com.Japon.Vuelo.Servicio;

import com.Japon.Vuelo.Repositorio.VueloRepositorio;
import org.springframework.stereotype.Service;
import com.Japon.Vuelo.Modelo.VueloGuardado;
import com.Japon.Vuelo.Seguridad.ClaveToken;
import org.springframework.http.HttpStatus;
import com.Japon.Vuelo.Seguridad.Token;
import java.util.Optional;
import java.util.List;
import java.util.Map;

@Service 
public class VueloServicio {

    private final VueloRepositorio Repositorio;

    public VueloServicio(VueloRepositorio Repositorio) {
        this.Repositorio = Repositorio;
    }

    public Long UsuarioDesdeToken(String TokenRecibido) {

        String [] Datos = Token.Validar(TokenRecibido, ClaveToken.SECRETO);

        if (Datos == null) {
            throw new VueloException(HttpStatus.UNAUTHORIZED, "[+] Debe Iniciar Sesion Para Guardar Vuelos");
        }

        return Long.parseLong(Datos [0]);

    }

    public String Texto(Map <String, Object> Body, String Campo) {
        Object Valor = Body.get(Campo);
        if (Valor == null || String.valueOf(Valor).trim().isEmpty()) {
            throw new VueloException(HttpStatus.BAD_REQUEST, "[+] Falta El Campo : " + Campo);
        }
        return String.valueOf(Valor).trim();
    }

    public Integer Numero(Object Valor) {
        try {
            return Integer.parseInt(String.valueOf(Valor));
        } catch (Exception e) {
            throw new VueloException(HttpStatus.BAD_REQUEST, "[+] Pasaje_ID Invalido");
        }
    }

    public List <VueloGuardado> Listar(Long Usuario) {
        return Repositorio.BuscarPorUsuario(Usuario);
    }

    public void Quitar(Long Usuario, Integer Pasaje) {
        VueloGuardado Vuelo = Repositorio.BuscarUno(Usuario, Pasaje)
            .orElseThrow(() -> new VueloException(HttpStatus.NOT_FOUND, "[+] Este Vuelo No Esta Guardado"));

        Repositorio.delete(Vuelo);
    }

    public VueloGuardado Guardar(Long Usuario, Map <String, Object> Body) {
        Integer Pasaje = Numero(Body.get("Pasaje_ID"));
        Optional <VueloGuardado> Existe = Repositorio.BuscarUno(Usuario, Pasaje);
        if (Existe.isPresent()) {
            return Existe.get();
        }

        VueloGuardado Nuevo = new VueloGuardado();
        Nuevo.setUsuario_ID(Usuario);
        Nuevo.setPasaje_ID(Pasaje);
        Nuevo.setCiudad(Texto(Body, "Ciudad"));
        Nuevo.setKanji(Texto(Body, "Kanji"));
        Nuevo.setCodigo(Texto(Body, "Codigo"));
        Nuevo.setVuelo(Texto(Body, "Vuelo"));
        Nuevo.setFecha(Texto(Body, "Fecha"));
        Nuevo.setHora(Texto(Body, "Hora"));
        Nuevo.setPuerta(Texto(Body, "Puerta"));
        Nuevo.setAsiento(Texto(Body, "Asiento"));
        Nuevo.setClase(Texto(Body, "Clase"));
        Nuevo.setPrecio(Texto(Body, "Precio"));
        Nuevo.setColor(Texto(Body, "Color"));

        return Repositorio.save(Nuevo);

    }

}