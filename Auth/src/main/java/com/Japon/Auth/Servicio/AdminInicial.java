package com.Japon.Auth.Servicio;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.Japon.Auth.Repositorio.UsuarioRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.Japon.Auth.Seguridad.CuentaAdmin;
import com.Japon.Auth.Modelo.Usuario;

@Component
public class AdminInicial implements CommandLineRunner {

    private final UsuarioRepositorio Repositorio;
    private final BCryptPasswordEncoder Encriptador;

    public AdminInicial(UsuarioRepositorio Repo, BCryptPasswordEncoder Encriptador) {
        this.Repositorio = Repo;
        this.Encriptador = Encriptador;
    }

    @Override 
    public void run(String ... args) {
        Usuario Admin = Repositorio.BuscarPorCorreo(CuentaAdmin.CORREO).orElse(new Usuario());

        Admin.setNombre_Completo(CuentaAdmin.NOMBRE);
        Admin.setCorreo(CuentaAdmin.CORREO.toLowerCase());
        Admin.setContraseña(Encriptador.encode(CuentaAdmin.CONTRASEÑA));

        Repositorio.save(Admin);

        System.out.println("[+] Cuenta Admin Lista : " + CuentaAdmin.CORREO + " ... ");

    }

}