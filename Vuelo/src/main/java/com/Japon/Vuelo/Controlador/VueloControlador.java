package com.Japon.Vuelo.Controlador;

import org.springframework.web.bind.annotation.*;
import com.Japon.Vuelo.Servicio.VueloException;
import org.springframework.http.ResponseEntity;
import com.Japon.Vuelo.Servicio.VueloServicio;
import com.Japon.Vuelo.Modelo.VueloGuardado;
import org.springframework.http.HttpStatus;
import com.Japon.Vuelo.Seguridad.Token;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController 
@RequestMapping("/Api/Vuelos")
@CrossOrigin(origins = "http://localhost:5173")
public class VueloControlador {

    private final VueloServicio Servicio;

    public VueloControlador(VueloServicio Servicio) {
        this.Servicio = Servicio;
    }

    private Map <String, Object> Datos(VueloGuardado V) {
        
        Map <String, Object> D = new LinkedHashMap <> ();

        D.put("ID", V.getID());
        D.put("Pasaje_ID", V.getPasaje_ID());
        D.put("Cuidad", V.getCiudad());
        D.put("Kanji", V.getKanji());
        D.put("Codigo", V.getCodigo());
        D.put("Vuelo", V.getVuelo());
        D.put("Fecha", V.getFecha());
        D.put("Hora", V.getHora());
        D.put("Puerta", V.getPuerta());
        D.put("Asiento", V.getAsiento());
        D.put("Clase", V.getClase());
        D.put("Precio", V.getPrecio());
        D.put("Color", V.getColor());
        D.put("Fecha_Guardado", V.getFecha_Guardado() != null ? V.getFecha_Guardado().toString() : null);

        return D;
    
    }

    @GetMapping
    public ResponseEntity <List <Map <String, Object>>> Listar(@RequestHeader(value = "Authorization", required = false) String Cabecera) {
        
        Long Usuario = Servicio.UsuarioDesdeToken(Token.DesdeCabecera(Cabecera));
        List <Map <String, Object> > Lista = new ArrayList <> ();
        for (VueloGuardado V : Servicio.Listar(Usuario)) {
            Lista.add(Datos(V));
        }
        return ResponseEntity.ok(Lista);
    
    }

    @PostMapping
    public ResponseEntity <Map <String, Object>> Guardar(@RequestHeader(value = "Authorization", required = false) String Cabecera, @RequestBody Map <String, Object> Body) {

        Long Usuario = Servicio.UsuarioDesdeToken(Token.DesdeCabecera(Cabecera));
        VueloGuardado Guardado = Servicio.Guardar(Usuario, Body);
        return ResponseEntity.status(HttpStatus.CREATED).body(Datos(Guardado));

    } 

    @DeleteMapping("/{PasajeID}")
    public ResponseEntity <Void> Quitar(@RequestHeader(value = "Authorization", required = false) String Cabacera, @PathVariable("PasajeID") Integer PasajeID) {
        
        Long Usuario = Servicio.UsuarioDesdeToken(Token.DesdeCabecera(Cabacera));
        Servicio.Quitar(Usuario, PasajeID);

        return ResponseEntity.noContent().build();
    
    }

    @ExceptionHandler(VueloException.class)
    public ResponseEntity <Map <String, Object>> Error(VueloException e) {

        return ResponseEntity.status(e.getEstado()).body(Map.of("Error", e.getMessage()));

    }

}