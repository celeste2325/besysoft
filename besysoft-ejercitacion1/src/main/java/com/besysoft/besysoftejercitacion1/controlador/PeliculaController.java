package com.besysoft.besysoftejercitacion1.controlador;

import com.besysoft.besysoftejercitacion1.dominio.Pelicula_Serie;
import com.besysoft.besysoftejercitacion1.service.interfaces.PeliculaService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.*;
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
    private final PeliculaService service;
    public PeliculaController(PeliculaService peliculaService) {
        this.service = peliculaService;
    }

    @GetMapping()
    public ResponseEntity<List<Pelicula_Serie>> obtenerTodos() {
        return new ResponseEntity<>(this.service.obtenerTodos(), HttpStatus.OK);
    }

    @GetMapping("/request-param")
    public ResponseEntity<?> buscarPorTituloOrGenero(@RequestParam(defaultValue = "") String titulo, @RequestParam(defaultValue = "") String genero) {

        try {
            return new ResponseEntity<>(this.service.buscarPeliculasPorTituloOrGenero(titulo, genero), HttpStatus.OK);
        } catch (BuscarPorGeneroOtituloException | GeneroInexistenteException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<Pelicula_Serie>> buscarPorRangoDeFecha(@RequestParam(defaultValue = "") @DateTimeFormat(pattern = "ddMMyyyy") LocalDate desde, @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "ddMMyyyy") LocalDate hasta) {
        return new ResponseEntity<>(this.service.buscarPeliculasPorRangoDeFecha(desde, hasta), HttpStatus.OK);
    }

    @GetMapping("/calificacion")
    public ResponseEntity<List<Pelicula_Serie>> buscarPorRangoDeCalificacion(@RequestParam(defaultValue = "") double desde, @RequestParam(defaultValue = "") double hasta) {
        return new ResponseEntity<>(this.service.buscarPeliculasPorRangoDeCalificacion(desde, hasta), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> AltaPelicula(@RequestBody Pelicula_Serie pelicula_serie) {

        try {
            return new ResponseEntity<>(this.service.altaPelicula(pelicula_serie), HttpStatus.CREATED);
        } catch (PeliculaExistenteConMismoTituloException | ElCampoTituloEsObligatorioException |
                 RangoDeCalificacionExcedidoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePelicula(@RequestBody Pelicula_Serie pelicula_serie, @PathVariable Long id) {
        try {
            return new ResponseEntity<>(this.service.updatePelicula(pelicula_serie, id), HttpStatus.OK);
        } catch (PeliculaExistenteConMismoTituloException | IdInexistente | RangoDeCalificacionExcedidoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
