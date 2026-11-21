package com.Japon.Vuelo.Servicio;

import org.springframework.http.HttpStatus;

public class VueloException extends RuntimeException {

    private final HttpStatus Estado;

    public VueloException(HttpStatus Estado, String Mensaje) {
        super(Mensaje);
        this.Estado = Estado;
    }

    public HttpStatus getEstado() {
        return Estado;
    }

}