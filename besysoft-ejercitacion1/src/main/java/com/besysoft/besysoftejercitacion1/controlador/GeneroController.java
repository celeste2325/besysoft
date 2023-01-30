package com.besysoft.besysoftejercitacion1.controlador;

import com.besysoft.besysoftejercitacion1.dominio.Genero;
import com.besysoft.besysoftejercitacion1.utilidades.DatosDummy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/generos")
public class GeneroController {
    private final DatosDummy datos;

    public GeneroController(DatosDummy datos) {
        this.datos = datos;
    }

    //TODO REVISAR CONTROLADOR GENERO Y PERSONAJE. YA ESTA REVISADO CONTROLADOR Y ENDOPOINTS QUE MANDARON DEL CONTROLER PELICULA

    @PostMapping()
    public ResponseEntity<?> AltaGenero(@RequestBody Genero genero) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("app-info", "celeste@bootcamp.com");

        //campos nombre es obligatorio
        if (!genero.getNombre().isEmpty()) {
            Genero generoEncontrado = datos.elGeneroExiste(genero);
            if(generoEncontrado == null) {
                genero.setId((long) (datos.getGeneros().size()+1));
                datos.getGeneros().add(genero);
                return new ResponseEntity<>(genero, headers, HttpStatus.CREATED);
            }
            //caso en el que se este agregando pelicula al genero
            else if (!generoEncontrado.getPeliculas_seriesAsociadas().containsAll(genero.getPeliculas_seriesAsociadas())) {
                //agrega las peliculas que no estan asignadas a ese genero
                genero.getPeliculas_seriesAsociadas().forEach(g -> {
                    if (!generoEncontrado.getPeliculas_seriesAsociadas().contains(g)) {
                        generoEncontrado.getPeliculas_seriesAsociadas().add(g);
                    }
                });
                return new ResponseEntity<>(genero, headers, HttpStatus.CREATED);

            } else {
                return ResponseEntity.badRequest().body("Ya existe una genero con mismo nombre");
            }

        }return ResponseEntity.badRequest().body("Los campos: Titulo y Fecha de creación son obligatorios");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGenero(@RequestBody Genero genero, @PathVariable Long id) {
        Genero generoEncontrado = this.datos.buscarGeneroById(id);
        if (generoEncontrado == null) {
            return ResponseEntity.badRequest().body("El genero que intenta modificar no existe");
        }
        generoEncontrado.setNombre(genero.getNombre());

        //valida que las peliculas que se quieren asociar al genero no esten cargados
        if (!generoEncontrado.getPeliculas_seriesAsociadas().containsAll(genero.getPeliculas_seriesAsociadas())) {
            genero.getPeliculas_seriesAsociadas().forEach(pelicula_serie ->
                    {
                        //encuentra aquellas peliculas q no estan cargadas y las agrega
                        if (!generoEncontrado.getPeliculas_seriesAsociadas().contains(pelicula_serie)) {
                            generoEncontrado.getPeliculas_seriesAsociadas().add(pelicula_serie);
                        }

                    }
            );
        }

        return new ResponseEntity<>(generoEncontrado, HttpStatus.OK);
    }
}
