package com.besysoft.besysoftejercitacion1.controlador;

import com.besysoft.besysoftejercitacion1.dominio.Pelicula_Serie;
import com.besysoft.besysoftejercitacion1.utilidades.DatosDummy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController {
    @Autowired
    DatosDummy datos;

    @GetMapping()
    public List<Pelicula_Serie> getPeliculas() {
        return datos.getPeliculas_series();
    }

    @GetMapping("/request-param")
    public Object buscarPeliculasPorTituloOrGenero(@RequestParam(defaultValue = "") String titulo, @RequestParam(defaultValue = "") String genero) {
        return this.datos.getPeliculasByTituloOrGenero(titulo,genero);
    }

    @GetMapping("/fechas")
    public Object buscarPeliculasPorRangoDeFecha(@RequestParam(defaultValue = "") @DateTimeFormat(pattern = "ddMMyyyy") LocalDate desde,
                                                 @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "ddMMyyyy") LocalDate hasta) {
        return this.datos.getPeliculasByFecha(desde, hasta);
    }

    @GetMapping("/calificacion")
    public Object buscarPeliculasPorRangoDeCalificacion(@RequestParam(defaultValue = "")double desde, @RequestParam(defaultValue = "") double hasta) {
        return this.datos.getPeliculasByRangoDeCalificacion(desde, hasta);
    }

    @PostMapping()
    public ResponseEntity<?> AltaPelicula(@RequestBody Pelicula_Serie pelicula_serie){
        //campos titulo y fecha de creacion son obligatorios
        //TODO
        //HACER VALIDACION DE QUE EL TITULO NO SE REPITA
        HttpHeaders headers = new HttpHeaders();
        headers.set("app-info", "celeste@bootcamp.com");

        if (pelicula_serie.getTitulo() != null && pelicula_serie.getFechaCreacion() != null) {
            if (this.datos.getPeliculas_series().stream().anyMatch(pelicula ->
                    pelicula.getTitulo().equalsIgnoreCase(pelicula_serie.getTitulo()) && pelicula.getFechaCreacion().isEqual(pelicula_serie.getFechaCreacion()))) {
                return ResponseEntity.badRequest().body("Ya existe una pelicula con mismo titulo y fecha de creacion");
            } else {
                pelicula_serie.setId((long) (this.datos.getPeliculas_series().size() + 1));
                this.datos.getPeliculas_series().add(pelicula_serie);
                return new ResponseEntity<>(pelicula_serie, headers, HttpStatus.CREATED);
            }
        } else {
            return ResponseEntity.badRequest().body("Los campos: Titulo y Fecha de creación son obligatorios");
        }
    }
}
