package com.Japon.Mensajeria.Controlador;

import com.Japon.Mensajeria.Servicio.MensajeriaServicio;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.Japon.Mensajeria.Modelo.Mensajeria;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Api/Contactos")
@CrossOrigin(origins = "http://localhost:5173")
public class MensajeriaControlador {

    private final MensajeriaServicio Servicio;

    public MensajeriaControlador(MensajeriaServicio Servicio) {
        this.Servicio = Servicio;
    }

    @PostMapping
    public ResponseEntity <Mensajeria> Guardar(@RequestBody Mensajeria Contacto) {
        Mensajeria Guardado = Servicio.Guardar(Contacto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(Guardado);

    }

    @GetMapping
    public List <Mensajeria> Listar() {
        return Servicio.Listar();
    }

    @PutMapping("/{ID}/Responder")
    public ResponseEntity <Mensajeria> Responder(@PathVariable("ID") Long ID, @RequestBody Map <String, String> Body) {
        String Respuesta = Body.get("Respuesta");
        if (Respuesta == null || Respuesta.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        } 
        return ResponseEntity.ok(Servicio.Responder(ID, Respuesta));
    } 

    @DeleteMapping("/{ID}")
    public ResponseEntity <Void> Eliminar(@PathVariable("ID") Long ID) {
        Servicio.Eliminar(ID);
        return ResponseEntity.noContent().build();
    }

}