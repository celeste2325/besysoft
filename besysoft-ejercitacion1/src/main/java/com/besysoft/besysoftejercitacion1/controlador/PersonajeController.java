package com.besysoft.besysoftejercitacion1.controlador;

import com.besysoft.besysoftejercitacion1.dominio.Personaje;
import com.besysoft.besysoftejercitacion1.utilidades.DatosDummy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personajes")
public class PersonajeController {
    private final DatosDummy datos;

    public PersonajeController(DatosDummy datos) {
        this.datos = datos;
    }

    @GetMapping()
    public List<Personaje> devolverTodosLosPersonajes() {
        return this.datos.getPersonajes();
    }

    @GetMapping("/request-param")
    public Object buscarPersonajesPorNombreOrEdad(@RequestParam(defaultValue = "") String nombre, @RequestParam(defaultValue = "0") int edad) {
        return this.datos.getPersonajesPorNombreOrEdad(nombre, edad);
    }

    @GetMapping("/edad")
    public Object buscarPersonajesPorRangoDeEdad(@RequestParam(defaultValue = "0") int desde, @RequestParam(defaultValue = "0") int hasta) {
        return this.datos.getPersonajesPorRangoDeEdad(desde, hasta);
    }

    @PostMapping()
    public ResponseEntity<?> AltaPersonaje(@RequestBody Personaje personaje) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("app-info", "celeste@bootcamp.com");

        //se asume campos nombre y edad son obligatorios
        if (!personaje.getNombre().isEmpty() && personaje.getEdad() != 0) {
            Personaje personajeEncontrado = datos.elPersonajeExiste(personaje);

            if (personajeEncontrado == null) {
                personaje.setId((long) (this.datos.getPersonajes().size() + 1));
                this.datos.getPersonajes().add(personaje);
                return new ResponseEntity<>(personaje, headers, HttpStatus.CREATED);
            }
//TODO REVISAR TODOS LOS CONSTAINSALL SI FUNCIONAN
            //caso en el que esta intentando agregar peliculas/series al personaje
            //ya existe el personaje
            else if (!personajeEncontrado.getPeliculas_series().containsAll(personaje.getPeliculas_series())) {
                personaje.getPeliculas_series().forEach(pelicula_serie -> {
                            //se agregan solo aquellas peliculas/series que no estan cargadas
                            if (!personajeEncontrado.getPeliculas_series().contains(pelicula_serie)) {
                                personajeEncontrado.getPeliculas_series().add(pelicula_serie);
                            }
                        }
                );
                return new ResponseEntity<>(personaje, headers, HttpStatus.CREATED);
            }
            return ResponseEntity.badRequest().body("Ya existe un personaje con mismo nombre y edad");
        }
        return ResponseEntity.badRequest().body("Los campos: Titulo y Fecha de creación son obligatorios");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePersonaje(@RequestBody Personaje personaje, @PathVariable Long id) {
        Personaje personajeEncontrado = this.datos.buscarPersonajeById(id);
        if (personajeEncontrado == null) {
            return ResponseEntity.badRequest().body("El personaje que intenta modificar no existe");
        }
        personajeEncontrado.setPeso(personaje.getPeso());
        personajeEncontrado.setEdad(personaje.getEdad());
        personajeEncontrado.setHistoria(personaje.getHistoria());
        personajeEncontrado.setNombre(personaje.getNombre());


        personajeEncontrado.getPeliculas_series();
        //valida que las peliculas que se quieren asociar al personaje no esten cargados
        if (!personajeEncontrado.getPeliculas_series().containsAll(personaje.getPeliculas_series())) {
            personaje.getPeliculas_series().forEach(pelicula ->
                    {
                        //encuentra aquellas pelicualas q no estan cargados y los agrega
                        if (!personajeEncontrado.getPeliculas_series().contains(pelicula)) {
                            personajeEncontrado.getPeliculas_series().add(pelicula);
                        }

                    }
            );
        }

        return new ResponseEntity<>(personajeEncontrado, HttpStatus.OK);
    }


}
