package com.besysoft.besysoftejercitacion1.controlador;

import com.besysoft.besysoftejercitacion1.dominio.Personaje;
import com.besysoft.besysoftejercitacion1.service.interfaces.PersonajeService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.BuscarPorEdadOPorNombreException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.ElPersonajeExisteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.NombreYEdadSonCamposObligatoriosException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.PersonajeInexistenteException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personajes")
public class PersonajeController {
    private final PersonajeService personajeService;

    public PersonajeController(PersonajeService personajeService) {
        this.personajeService = personajeService;
    }

    @GetMapping()
    public ResponseEntity<List<Personaje>> devolverTodosLosPersonajes() {
        return new ResponseEntity<>(this.personajeService.obtenerTodos(), HttpStatus.OK);
    }

    @GetMapping("/request-param")
    public ResponseEntity<?> buscarPersonajesPorNombreOrEdad(@RequestParam(defaultValue = "") String nombre, @RequestParam(defaultValue = "0") int edad) {
        try {
            return new ResponseEntity<>(this.personajeService.buscarPersonajesPorNombreOrEdad(nombre, edad), HttpStatus.OK);
        } catch (BuscarPorEdadOPorNombreException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/edad")
    public Object buscarPersonajesPorRangoDeEdad(@RequestParam(defaultValue = "0") int desde, @RequestParam(defaultValue = "0") int hasta) {
        return this.personajeService.buscarPersonajesPorRangoDeEdad(desde, hasta);
    }

    @PostMapping()
    public ResponseEntity<?> AltaPersonaje(@RequestBody Personaje personaje) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("app-info", "celeste@bootcamp.com");

        try {
            return new ResponseEntity<>(this.personajeService.altaPersonaje(personaje), headers, HttpStatus.CREATED);
        } catch (ElPersonajeExisteException | NombreYEdadSonCamposObligatoriosException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePersonaje(@RequestBody Personaje personaje, @PathVariable Long id) {
        try {
            return new ResponseEntity<>(this.personajeService.updatePersonaje(personaje, id), HttpStatus.OK);
        } catch (PersonajeInexistenteException | ElPersonajeExisteException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
