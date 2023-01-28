package com.besysoft.besysoftejercitacion1.controlador;

import com.besysoft.besysoftejercitacion1.dominio.Personaje;
import com.besysoft.besysoftejercitacion1.utilidades.DatosDummy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/personajes")
public class PersonajeController {
    @Autowired
    DatosDummy datos;

    @GetMapping()
    public List<Personaje> devolverTodosLosPersonajes() {
        return this.datos.getPersonajes();
    }

    @GetMapping("/request-param")
    public Object buscarPersonajesPorNombreOrEdad(@RequestParam(defaultValue = "") String nombre,@RequestParam(defaultValue = "0") int edad) {
        return this.datos.getPersonajesPorNombreOrEdad(nombre,edad);
    }

    @GetMapping("/edad")
    public Object buscarPersonajesPorRangoDeEdad(@RequestParam(defaultValue = "0") int desde,@RequestParam(defaultValue = "0") int hasta) {
        return this.datos.getPersonajesPorRangoDeEdad(desde,hasta);
    }


}
