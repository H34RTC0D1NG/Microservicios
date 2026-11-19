package com.Japon.Mensajeria.Servicio;

import com.Japon.Mensajeria.Repositorio.MensajeriaRepositorio;
import org.springframework.web.server.ResponseStatusException;
import com.Japon.Mensajeria.Modelo.Mensajeria;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensajeriaServicio {

    private final MensajeriaRepositorio Repositorio;

    public MensajeriaServicio(MensajeriaRepositorio Repo) {
        this.Repositorio = Repo;
    }

    public Mensajeria Guardar(Mensajeria Contacto) {
        Contacto.setID(null);
        Contacto.setEstado("Pendiente");
        Contacto.setRespuesta(null);
        Contacto.setFecha_Respuesta(null);
        return Repositorio.save(Contacto);
    }

    public List <Mensajeria> Listar() {
        return Repositorio.findAll();
    }

    public Mensajeria Responder(Long ID, String Respuesta) {
        Mensajeria Contacto = Repositorio.findById(ID)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "[+] Mensaje No Encontrado ..."));

        Contacto.setRespuesta(Respuesta.trim());
        Contacto.setEstado("Respondido");
        Contacto.setFecha_Respuesta(LocalDateTime.now());

        return Repositorio.save(Contacto);
    }

    public void Eliminar(Long ID) {
        if (!Repositorio.existsById(ID)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "[+] Mensaje No Encontrado ...");
        }

        Repositorio.deleteById(ID);

    }

}