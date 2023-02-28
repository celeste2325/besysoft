package com.besysoft.besysoftejercitacion1.controlador;

import com.besysoft.besysoftejercitacion1.dominio.dto.Pelicula_serieDto;
import com.besysoft.besysoftejercitacion1.dominio.dto.Pelicula_serieResponseDto;
import com.besysoft.besysoftejercitacion1.dominio.entity.Pelicula_Serie;
import com.besysoft.besysoftejercitacion1.dominio.mapstruct.Pelicula_serieMapper;
import com.besysoft.besysoftejercitacion1.service.interfaces.PeliculaService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.GeneroInexistenteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.IdInexistente;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.PeliculaExistenteConMismoTituloException;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class PeliculaController {
    private final PeliculaService service;
    private final Pelicula_serieMapper pelicula_serieMapper;
    @GetMapping()
    public ResponseEntity<List<Pelicula_serieResponseDto>> obtenerTodos() {
        List<Pelicula_Serie> pelicula_series = this.service.obtenerTodos();

        List<Pelicula_serieResponseDto> pelicula_serieDtos = pelicula_serieMapper.mapLisToDto(pelicula_series);
        return new ResponseEntity<>(pelicula_serieDtos, HttpStatus.OK);
    }


    @GetMapping("/request-param")
    public ResponseEntity<?> buscarPorTituloOrGenero(@RequestParam(defaultValue = "") String titulo, @RequestParam(defaultValue = "") String genero) {
        //no admite AND es or
        if (!genero.equalsIgnoreCase("") && !titulo.equalsIgnoreCase("")) {
            return ResponseEntity.badRequest().body("Debe Buscar o por titulo o por Genero. No se admiten ambos");
        }
        try {
            List<Pelicula_Serie> pelicula_series = this.service.buscarPeliculasPorTituloOrGenero(titulo, genero);

            List<Pelicula_serieResponseDto> pelicula_serieDtos = pelicula_serieMapper.mapLisToDto(pelicula_series);
            return new ResponseEntity<>(pelicula_serieDtos, HttpStatus.OK);
        } catch (GeneroInexistenteException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<Pelicula_serieResponseDto>> buscarPorRangoDeFecha(@RequestParam(defaultValue = "") @DateTimeFormat(pattern = "ddMMyyyy") LocalDate desde, @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "ddMMyyyy") LocalDate hasta) {
        List<Pelicula_Serie> pelicula_series = this.service.buscarPeliculasPorRangoDeFecha(desde, hasta);

        List<Pelicula_serieResponseDto> pelicula_serieDtos = pelicula_serieMapper.mapLisToDto(pelicula_series);
        return new ResponseEntity<>(pelicula_serieDtos, HttpStatus.OK);
    }

    @GetMapping("/calificacion")
    public ResponseEntity<List<Pelicula_serieResponseDto>> buscarPorRangoDeCalificacion(@RequestParam(defaultValue = "") double desde, @RequestParam(defaultValue = "") double hasta) {
        List<Pelicula_Serie> pelicula_series = this.service.buscarPeliculasPorRangoDeCalificacion(desde, hasta);

        List<Pelicula_serieResponseDto> pelicula_serieDtos = pelicula_serieMapper.mapLisToDto(pelicula_series);
        return new ResponseEntity<>(pelicula_serieDtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> AltaPelicula(@Valid @RequestBody Pelicula_serieDto pelicula_serieDto, BindingResult result) {
        Map<String, Object> validaciones = new HashMap<>();
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(fieldError -> validaciones.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(validaciones);
        }
        try {
            Pelicula_Serie pelicula_serie = pelicula_serieMapper.mapToEntity(pelicula_serieDto);
            Pelicula_Serie pelicula_serieSave = this.service.altaPelicula(pelicula_serie);
            Pelicula_serieResponseDto pelicula_serieResponseDto = pelicula_serieMapper.mapToDto(pelicula_serieSave);

            return new ResponseEntity<>(pelicula_serieResponseDto, HttpStatus.CREATED);
        } catch (PeliculaExistenteConMismoTituloException | IdInexistente e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePelicula(@Valid @RequestBody Pelicula_serieDto pelicula_serieDto, BindingResult result, @PathVariable Long id) {
        Map<String, Object> validaciones = new HashMap<>();
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(fieldError -> validaciones.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(validaciones);
        }
        try {
            Pelicula_Serie pelicula_serie = pelicula_serieMapper.mapToEntity(pelicula_serieDto);
            Pelicula_Serie pelicula_serieSave = this.service.updatePelicula(pelicula_serie, id);
            Pelicula_serieResponseDto pelicula_serieResponseDto = pelicula_serieMapper.mapToDto(pelicula_serieSave);

            return new ResponseEntity<>(pelicula_serieResponseDto, HttpStatus.OK);
        } catch (PeliculaExistenteConMismoTituloException | IdInexistente e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
