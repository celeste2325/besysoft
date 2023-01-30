package com.besysoft.besysoftejercitacion1.controlador;

import com.besysoft.besysoftejercitacion1.dominio.Pelicula_Serie;
import com.besysoft.besysoftejercitacion1.dominio.Personaje;
import com.besysoft.besysoftejercitacion1.utilidades.DatosDummy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/personajes")
public class PersonajeController {
    private final DatosDummy datos;
    public PersonajeController(DatosDummy datos) {
        this.datos = datos;
    }

    @GetMapping()
    public ResponseEntity<List<Personaje>> devolverTodosLosPersonajes() {
        return new ResponseEntity<>(this.datos.getPersonajes(), HttpStatus.OK);
    }

    @GetMapping("/request-param")
    public ResponseEntity<?> buscarPersonajesPorNombreOrEdad(@RequestParam(defaultValue = "") String nombre, @RequestParam(defaultValue = "0") int edad) {
        return new ResponseEntity<>(this.datos.getPersonajesPorNombreOrEdad(nombre, edad), HttpStatus.OK);
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
        if (personaje.getNombre() != null && personaje.getEdad() != 0) {
            if (datos.buscarPersonajeConMismoNombreYedad(personaje) != null) {
                return ResponseEntity.badRequest().body("Ya existe el personaje");
            }
            personaje.setId((long) (this.datos.getPersonajes().size() + 1));
            this.datos.getPersonajes().add(personaje);
            return new ResponseEntity<>(personaje, headers, HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().body("Los campos: nombre y edad son obligatorios");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePersonaje(@RequestBody Personaje personaje, @PathVariable Long id) {
        Personaje personajeEncontradoById = this.datos.buscarPersonajeById(id);
        Personaje personajeEncontradoByNombreAndEdad = this.datos.buscarPersonajeConMismoNombreYedad(personaje);

        if (personajeEncontradoById != null) {
            if (personajeEncontradoByNombreAndEdad != null && !Objects.equals(personajeEncontradoByNombreAndEdad.getId(), id)) {
                return ResponseEntity.badRequest().body("Ya existe otro personaje con mismo nombre y edad");
            }
            if (personaje.getPeso() != 0.0) {
                personajeEncontradoById.setPeso(personaje.getPeso());
            }
            if (personaje.getEdad() != 0) {
                personajeEncontradoById.setEdad(personaje.getEdad());
            }
            if (personaje.getHistoria() != null) {
                personajeEncontradoById.setHistoria(personaje.getHistoria());
            }

            if (personaje.getNombre() != null) {
                personajeEncontradoById.setNombre(personaje.getNombre());
            }

            //valida que las peliculas que se quieren asociar al personaje no esten cargadas
            personaje.getPeliculas_series().forEach(pelicula ->
                    {
                        //encuentra aquellas peliculas q no estan cargados al personaje y las agrega
                        if (!personajeEncontradoById.getPeliculas_series().contains(pelicula)) {

                            //busca la pelicula por su titulo esto para no tener que pasar el obj completo por el body
                            Pelicula_Serie peliculaEncontrada = this.datos.buscarPeliculaConMismoTitulo(pelicula);

                            //para mantener consistencia si la pelicula que se intenta agregar no existe la crea
                            if (peliculaEncontrada == null) {
                                //agregar metodo dar alta cuando lo saque del controller y pase al service
                            }else {
                                //si no esta cargada la pelicula al personaje lo agrega
                                personajeEncontradoById.getPeliculas_series().add(peliculaEncontrada);

                                //actualiza la lista de personajes asiciados a esa pelicula para mantener consistencia
                                if (!peliculaEncontrada.getPersonajesAsociados().contains(personajeEncontradoById)) {
                                    peliculaEncontrada.getPersonajesAsociados().add(personajeEncontradoById);
                                }
                            }
                        }
                    }
            );
            return new ResponseEntity<>(personajeEncontradoById, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body("El personaje que intenta modificar no existe");

    }


}
