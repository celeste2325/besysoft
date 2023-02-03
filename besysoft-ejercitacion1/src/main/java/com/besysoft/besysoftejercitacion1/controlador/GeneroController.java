package com.besysoft.besysoftejercitacion1.controlador;

import com.besysoft.besysoftejercitacion1.dominio.Genero;
import com.besysoft.besysoftejercitacion1.dominio.Pelicula_Serie;
import com.besysoft.besysoftejercitacion1.utilidades.DatosDummy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/generos")
public class GeneroController {
    //TODO FALTA REFACTOR GENERO
    private final DatosDummy datos;

    public GeneroController(DatosDummy datos) {
        this.datos = datos;
    }
    @PostMapping()
    public ResponseEntity<?> AltaGenero(@RequestBody Genero genero) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("app-info", "celeste@bootcamp.com");

        //campo nombre es obligatorio
        if (genero.getNombre() != null) {
            if (datos.buscarGeneroPorNombre(genero) == null) {
                genero.setId((long) (datos.getGeneros().size() + 1));
                datos.getGeneros().add(genero);
                return new ResponseEntity<>(genero, headers, HttpStatus.CREATED);
            } else {
                return ResponseEntity.badRequest().body("Ya existe una genero con mismo nombre");
            }
        }
        return ResponseEntity.badRequest().body("El campo nombre es obligatorio");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGenero(@RequestBody Genero genero, @PathVariable Long id) {
        Genero generoEncontradoById = this.datos.buscarGeneroPorId(id);
        Genero generoEncontradoByNombre = this.datos.buscarGeneroPorNombre(genero);

        if (generoEncontradoById != null) {
            if (generoEncontradoByNombre != null && !Objects.equals(generoEncontradoByNombre.getId(), id)) {
                ResponseEntity.badRequest().body("Ya existe un genero con mismo nombre");
            }
            if (generoEncontradoById.getNombre() != null) {
                generoEncontradoById.setNombre(genero.getNombre());
            }
            //valida que las peliculas que se quieren asociar al genero no esten cargados
            genero.getPeliculas_seriesAsociadas().forEach(pelicula_serie ->
                    {
                        //encuentra aquellas peliculas q no estan cargadas y las agrega
                        if (!generoEncontradoById.getPeliculas_seriesAsociadas().contains(pelicula_serie)) {

                            //busca la pelicula por su titulo, esto para no tener que pasar el obj completo por el body
                            Pelicula_Serie peliculaEncontradaByTitulo = this.datos.buscarPeliculaPorTitulo(pelicula_serie);

                            if (peliculaEncontradaByTitulo == null) {
                                //agregar metodo dar alta cuando lo saque del controller y pase al service
                            } else {
                                generoEncontradoById.getPeliculas_seriesAsociadas().add(peliculaEncontradaByTitulo);
                            }
                        }
                    }
            );
            return new ResponseEntity<>(generoEncontradoById, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body("El genero que intenta modificar no existe");
    }
}
