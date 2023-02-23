package com.besysoft.besysoftejercitacion1.controlador;

import com.besysoft.besysoftejercitacion1.dominio.Pelicula_Serie;
import com.besysoft.besysoftejercitacion1.service.interfaces.PeliculaService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.GeneroInexistenteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.IdInexistente;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.PeliculaExistenteConMismoTituloException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        //no admite AND es or
        if (!genero.equalsIgnoreCase("") && !titulo.equalsIgnoreCase("")) {
            return ResponseEntity.badRequest().body("Debe Buscar o por titulo o por Genero. No se admiten ambos");
        }
        try {
            return new ResponseEntity<>(this.service.buscarPeliculasPorTituloOrGenero(titulo, genero), HttpStatus.OK);
        } catch (GeneroInexistenteException e) {
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
    public ResponseEntity<?> AltaPelicula(@Valid @RequestBody Pelicula_Serie pelicula_serie, BindingResult result) {
        Map<String, Object> validaciones = new HashMap<>();
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(fieldError -> validaciones.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(validaciones);
        }
        try {
            return new ResponseEntity<>(this.service.altaPelicula(pelicula_serie), HttpStatus.CREATED);
        } catch (PeliculaExistenteConMismoTituloException | IdInexistente e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePelicula(@Valid @RequestBody Pelicula_Serie pelicula_serie, BindingResult result, @PathVariable Long id) {
        Map<String, Object> validaciones = new HashMap<>();
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(fieldError -> validaciones.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(validaciones);
        }
        try {
            return new ResponseEntity<>(this.service.updatePelicula(pelicula_serie, id), HttpStatus.OK);
        } catch (PeliculaExistenteConMismoTituloException | IdInexistente e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
