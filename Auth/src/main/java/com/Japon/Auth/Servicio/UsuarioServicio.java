package com.Japon.Auth.Servicio;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.Japon.Auth.Repositorio.UsuarioRepositorio;
import org.springframework.stereotype.Service;
import com.Japon.Auth.Seguridad.CuentaAdmin;
import com.Japon.Auth.Seguridad.ClaveToken;
import org.springframework.http.HttpStatus;
import com.Japon.Auth.Seguridad.Token;
import com.Japon.Auth.Modelo.Usuario;
import java.util.Optional;

@Service
public class UsuarioServicio {

    private final UsuarioRepositorio Repositorio;
    private final BCryptPasswordEncoder Encriptador;

    public UsuarioServicio(UsuarioRepositorio Repo, BCryptPasswordEncoder Encriptador) {
        this.Repositorio = Repo;
        this.Encriptador = Encriptador;
    }

    public Usuario Registrar(String Nombre, String Correo, String Contraseña) {
        if (Nombre == null || Nombre.trim().isEmpty()) {
            throw new UsuarioException(HttpStatus.BAD_REQUEST, "[+] Debe Ingresar Su Nombre ");
        } if (Correo == null || !Correo.trim().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new UsuarioException(HttpStatus.BAD_REQUEST, "[+] Debe Ingresar Un Correo Valido");
        } if (Contraseña == null || Contraseña.length() < 6) {
            throw new UsuarioException(HttpStatus.BAD_REQUEST, "[+] La Contraseña Debe Tener Al Menos 6 Caracters");
        }

        String CorreoLimpio = Correo.trim().toLowerCase();

        if (CorreoLimpio.equalsIgnoreCase(CuentaAdmin.CORREO)) {
            throw new UsuarioException(HttpStatus.CONFLICT, "[+] Ese Correo No Esta Disponible ");
        } if (Repositorio.BuscarPorCorreo(CorreoLimpio).isPresent()) {
            throw new UsuarioException(HttpStatus.CONFLICT, "[+] Ya Existe Una Cuenta Con Ese Correo ");
        } 

        Usuario Nuevo = new Usuario();
        Nuevo.setNombre_Completo(Nombre.trim());
        Nuevo.setCorreo(CorreoLimpio);
        Nuevo.setContraseña(Encriptador.encode(Contraseña));
        Nuevo.setRol("Usuario");

        return Repositorio.save(Nuevo);

    }

    public String IniciarSesion(String Correo, String Contraseña) {
        if (Correo == null || Contraseña == null) {
            throw new UsuarioException(HttpStatus.BAD_REQUEST, "[+] Debe Ingresar Correo y Contraseña");
        }

        Optional <Usuario> Buscado = Repositorio.BuscarPorCorreo(Correo.trim());

        if (Buscado.isEmpty() || !Encriptador.matches(Contraseña, Buscado.get().getContraseña())) {
            throw new UsuarioException(HttpStatus.UNAUTHORIZED, "[+] Correo o Contraseña Incorrectos");
        }

        Usuario U = Buscado.get();
        return Token.Crear(U.getID(), U.getCorreo(), U.getRol(), ClaveToken.SECRETO, ClaveToken.HORAS);
    }

    public Usuario ObtenerPerfil(String TokenRecibido) {
        String [] Datos = Token.Validar(TokenRecibido, ClaveToken.SECRETO);
        if (Datos == null) {
            throw new UsuarioException(HttpStatus.UNAUTHORIZED, "[+] Sesion Expirada, Inicie Sesion De Nuevo");
        }

        Long ID = Long.parseLong(Datos [0]);

        return Repositorio.findById(ID)
            .orElseThrow(() -> new UsuarioException(HttpStatus.UNAUTHORIZED, "[+] El Usuario Ya No Existe"));
    }

    public Usuario BuscarPorCorreo(String Correo) {
        return Repositorio.BuscarPorCorreo(Correo).orElse(null);
    }

}