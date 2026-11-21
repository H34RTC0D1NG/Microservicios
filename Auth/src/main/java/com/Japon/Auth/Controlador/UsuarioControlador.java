package com.Japon.Auth.Controlador;

import org.springframework.web.bind.annotation.*;
import com.Japon.Auth.Servicio.UsuarioException;
import org.springframework.http.ResponseEntity;
import com.Japon.Auth.Servicio.UsuarioServicio;
import org.springframework.http.HttpStatus;
import com.Japon.Auth.Seguridad.Token;
import com.Japon.Auth.Modelo.Usuario;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController 
@RequestMapping("/Api/Usuarios")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioControlador {

    private final UsuarioServicio Servicio;

    public UsuarioControlador(UsuarioServicio Servicio) {
        this.Servicio = Servicio;
    }

    @PostMapping("/Registro")
    public ResponseEntity <Map <String, Object>> Registro(@RequestBody Map <String, String> Body) {
        Usuario Nuevo = Servicio.Registrar(
            Body.get("Nombre_Completo"),
            Body.get("Correo"),
            Body.get("Contraseña")
        );

        Map <String, Object> Respuesta = new LinkedHashMap <> ();

        Respuesta.put("Mensaje", "[+] Cuenta Creada Exitosamente");
        Respuesta.put("Usuario", DatosPublicos(Nuevo));

        return ResponseEntity.status(HttpStatus.CREATED).body(Respuesta);

    }

    @PostMapping("/Login")
    public ResponseEntity <Map <String, Object>> Login(@RequestBody Map <String, String> Body) {
        String NuevoToken = Servicio.IniciarSesion(Body.get("Correo"), Body.get("Contraseña"));
        Usuario U = Servicio.BuscarPorCorreo(Body.get("Correo").trim());

        Map <String, Object> Respuesta = new LinkedHashMap <> ();
        Respuesta.put("Token", NuevoToken);
        Respuesta.put("Usuario", DatosPublicos(U));

        return ResponseEntity.ok(Respuesta);
    }

    @GetMapping("/Perfil")
    public ResponseEntity <Map <String, Object>> Perfil(@RequestHeader(value = "Authorization", required = false) String Cabecera) {
        Usuario U = Servicio.ObtenerPerfil(Token.DesdeCabecera(Cabecera));
        return ResponseEntity.ok(DatosPublicos(U));
    }

    @ExceptionHandler(UsuarioException.class)
    public ResponseEntity <Map <String, String>> Error(UsuarioException e) {
        return ResponseEntity.status(e.getEstado()).body(Map.of("Error", e.getMessage()));
    }

    private Map <String, Object> DatosPublicos(Usuario U) {
        Map <String, Object> Datos = new LinkedHashMap <> ();

        Datos.put("ID", U.getID());
        Datos.put("Nombre_Completo", U.getNombre_Completo());
        Datos.put("Correo", U.getCorreo());
        Datos.put("Rol", U.getRol());
        Datos.put("Fecha_Registro", U.getFecha_Registro() != null ? U.getFecha_Registro().toString() : null);

        return Datos;

    }

}