package com.besysoft.besysoftejercitacion1.controlador;

import com.besysoft.besysoftejercitacion1.dominio.Pelicula_Serie;
import com.besysoft.besysoftejercitacion1.dominio.Personaje;
import com.besysoft.besysoftejercitacion1.utilidades.DatosDummy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController {
    private final DatosDummy datos;

    public PeliculaController(DatosDummy datos) {
        this.datos = datos;
    }

    @GetMapping()
    public ResponseEntity<List<Pelicula_Serie>> getPeliculas() {
        return new ResponseEntity<>(datos.getPeliculas_series(), HttpStatus.OK);
    }

    @GetMapping("/request-param")
    public ResponseEntity<?> buscarPeliculasPorTituloOrGenero(@RequestParam(defaultValue = "") String titulo, @RequestParam(defaultValue = "") String genero) {
        return new ResponseEntity<>(this.datos.getPeliculasByTituloOrGenero(titulo, genero), HttpStatus.OK);
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<Pelicula_Serie>> buscarPeliculasPorRangoDeFecha(@RequestParam(defaultValue = "") @DateTimeFormat(pattern = "ddMMyyyy") LocalDate desde,
                                                                               @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "ddMMyyyy") LocalDate hasta) {
        return new ResponseEntity<>(this.datos.getPeliculasByFecha(desde, hasta), HttpStatus.OK);
    }

    @GetMapping("/calificacion")
    public ResponseEntity<List<Pelicula_Serie>> buscarPeliculasPorRangoDeCalificacion(@RequestParam(defaultValue = "") double desde, @RequestParam(defaultValue = "") double hasta) {
        return new ResponseEntity<>(this.datos.getPeliculasByRangoDeCalificacion(desde, hasta), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> AltaPelicula(@RequestBody Pelicula_Serie pelicula_serie) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("app-info", "celeste@bootcamp.com");

        //se asumen obligatorios los campos titulo y fecha de creacion.
        if (pelicula_serie.getTitulo() != null && pelicula_serie.getFechaCreacion() != null) {
            //la pelicula existe
            if (datos.existePeliculaConMismoTitulo(pelicula_serie) != null) {
                return ResponseEntity.badRequest().body("Ya existe una pelicula con mismo titulo");
            } else {
                pelicula_serie.setId((long) (this.datos.getPeliculas_series().size() + 1));
                this.datos.getPeliculas_series().add(pelicula_serie);
                return new ResponseEntity<>(pelicula_serie, headers, HttpStatus.CREATED);
            }
        } else {
            return ResponseEntity.badRequest().body("Los campos: Titulo y Fecha de creación son obligatorios");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePelicula(@RequestBody Pelicula_Serie pelicula_serie, @PathVariable Long id) {
        Pelicula_Serie peliculaEncontradaPorId = this.datos.buscarPeliculaById(id);
        Pelicula_Serie peliculaEncontradaPorTitulo = datos.existePeliculaConMismoTitulo(pelicula_serie);

        //existe para ser modificada
        if (peliculaEncontradaPorId != null) {
            //valida que al modificar la pelicula no se cambie el titulo por uno ya existente
            if (peliculaEncontradaPorTitulo != null && !Objects.equals(peliculaEncontradaPorTitulo.getId(), id)) {
                return ResponseEntity.badRequest().body(format("Ya existe una pelicula con mismo titulo: %s", pelicula_serie.getTitulo()));
            }

            //si vienen datos nulos o vacios no se sobreescriben
            if (pelicula_serie.getTitulo() != null) {
                peliculaEncontradaPorId.setTitulo(pelicula_serie.getTitulo());
            }
            if (pelicula_serie.getCalificacion() != 0.0) {
                peliculaEncontradaPorId.setCalificacion(pelicula_serie.getCalificacion());
            }
            if (pelicula_serie.getFechaCreacion() != null) {
                peliculaEncontradaPorId.setFechaCreacion(pelicula_serie.getFechaCreacion());
            }

            //editar lista de pesonajes asociados a la pelicula
            pelicula_serie.getPersonajesAsociados().forEach(personaje ->
                    {
                        //encuentra aquellos personajes q no estan cargados y los agrega
                        if (!peliculaEncontradaPorId.getPersonajesAsociados().contains(personaje)) {
                            //busca el personaje con ese nombre y edad (asumiendo q son sus datos unicos) esto para no tener que pasar el obj completo por el body
                            Personaje personajeEncontrado = datos.getPersonajes().stream().
                                    filter(p -> p.getNombre()
                                            .equalsIgnoreCase(personaje.getNombre()) && p.getEdad() == personaje.getEdad()).findAny().
                                    orElse(null);
                            //si no esta cargado el personaje a la pelicula lo agrega
                            peliculaEncontradaPorId.getPersonajesAsociados().add(personajeEncontrado);

                            //actualiza tambien la lista de peliculas asociadas al personaje, siempre que ya no exista la pelicula en la lista. Para mantener consistencia
                            if (!personajeEncontrado.getPeliculas_series().contains(peliculaEncontradaPorId)) {
                                personajeEncontrado.addPelicula_serie(peliculaEncontradaPorId);
                            }
                        }
                    }
            );
            return new ResponseEntity<>(peliculaEncontradaPorId, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body(format("El ID %d ingresado no existe", id));
    }
}
