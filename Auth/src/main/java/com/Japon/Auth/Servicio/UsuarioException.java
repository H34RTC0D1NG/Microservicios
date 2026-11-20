package com.Japon.Auth.Servicio;

import org.springframework.http.HttpStatus;

public class UsuarioException extends RuntimeException {

    private final HttpStatus Estado;

    public UsuarioException(HttpStatus Estado, String Mensaje) {
        super(Mensaje);
        this.Estado = Estado;
    }

    public HttpStatus getEstado() {
        return Estado;
    }

}