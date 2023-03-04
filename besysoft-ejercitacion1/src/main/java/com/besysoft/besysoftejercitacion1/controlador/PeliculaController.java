package com.besysoft.besysoftejercitacion1.controlador;

import com.besysoft.besysoftejercitacion1.dominio.dto.Pelicula_serieDto;
import com.besysoft.besysoftejercitacion1.dominio.dto.Pelicula_serieResponseDto;
import com.besysoft.besysoftejercitacion1.dominio.entity.Pelicula_Serie;
import com.besysoft.besysoftejercitacion1.dominio.mapstruct.Pelicula_serieMapper;
import com.besysoft.besysoftejercitacion1.excepciones.IdInexistenteException;
import com.besysoft.besysoftejercitacion1.excepciones.PeliculaExistenteException;
import com.besysoft.besysoftejercitacion1.service.interfaces.PeliculaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/peliculas")
@AllArgsConstructor
@Slf4j
public class PeliculaController {
    private final PeliculaService service;
    private final Pelicula_serieMapper pelicula_serieMapper;

    @GetMapping()
    public ResponseEntity<List<Pelicula_serieResponseDto>> obtenerTodos() {
        List<Pelicula_Serie> pelicula_series = this.service.obtenerTodos();
        return new ResponseEntity<>(pelicula_serieMapper.mapLisToDto(pelicula_series), HttpStatus.OK);
    }

    @GetMapping("/request-param")
    public ResponseEntity<?> buscarPorTituloOrGenero(@RequestParam(defaultValue = "") String titulo, @RequestParam(defaultValue = "") String genero) throws IdInexistenteException {
        //no admite AND es or
        if (!genero.equalsIgnoreCase("") && !titulo.equalsIgnoreCase("")) {
            log.error("ocurrio un error porque la búsqueda fue realizada por titulo de pelicula Y genero. La misma debe ser por titulo O por genero");
            return ResponseEntity.badRequest().body("Debe Buscar o por titulo o por Genero. No se admiten ambos");
        }
        List<Pelicula_Serie> pelicula_series = this.service.buscarPeliculasPorTituloOrGenero(titulo, genero);
        return new ResponseEntity<>(pelicula_serieMapper.mapLisToDto(pelicula_series), HttpStatus.OK);
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<Pelicula_serieResponseDto>> buscarPorRangoDeFecha(@RequestParam(defaultValue = "") @DateTimeFormat(pattern = "ddMMyyyy") LocalDate desde, @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "ddMMyyyy") LocalDate hasta) {
        List<Pelicula_Serie> pelicula_series = this.service.buscarPeliculasPorRangoDeFecha(desde, hasta);
        return new ResponseEntity<>(pelicula_serieMapper.mapLisToDto(pelicula_series), HttpStatus.OK);
    }

    @GetMapping("/calificacion")
    public ResponseEntity<List<Pelicula_serieResponseDto>> buscarPorRangoDeCalificacion(@RequestParam(defaultValue = "") double desde, @RequestParam(defaultValue = "") double hasta) {
        List<Pelicula_Serie> pelicula_series = this.service.buscarPeliculasPorRangoDeCalificacion(desde, hasta);
        return new ResponseEntity<>(pelicula_serieMapper.mapLisToDto(pelicula_series), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> AltaPelicula(@Valid @RequestBody Pelicula_serieDto pelicula_serieDto) throws PeliculaExistenteException, IdInexistenteException {
        Pelicula_Serie pelicula_serie = pelicula_serieMapper.mapToEntity(pelicula_serieDto);
        Pelicula_Serie pelicula_serieSave = this.service.altaPelicula(pelicula_serie);
        return new ResponseEntity<>(pelicula_serieMapper.mapToDto(pelicula_serieSave), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePelicula(@Valid @RequestBody Pelicula_serieDto pelicula_serieDto, @PathVariable Long id) throws IdInexistenteException, PeliculaExistenteException {
        Pelicula_Serie pelicula_serie = pelicula_serieMapper.mapToEntity(pelicula_serieDto);
        Pelicula_Serie pelicula_serieSave = this.service.updatePelicula(pelicula_serie, id);
        return new ResponseEntity<>(pelicula_serieMapper.mapToDto(pelicula_serieSave), HttpStatus.OK);
    }
}
