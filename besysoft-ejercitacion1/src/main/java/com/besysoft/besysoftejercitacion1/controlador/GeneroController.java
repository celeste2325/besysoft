package com.besysoft.besysoftejercitacion1.controlador;

import com.besysoft.besysoftejercitacion1.dominio.dto.GeneroDto;
import com.besysoft.besysoftejercitacion1.dominio.entity.Genero;
import com.besysoft.besysoftejercitacion1.dominio.mapper.GeneroMapper;
import com.besysoft.besysoftejercitacion1.service.interfaces.GeneroService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.GeneroInexistenteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.YaExisteGeneroConMismoNombreException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/generos")
public class GeneroController {

    private final GeneroService generoService;

    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    @PostMapping()
    public ResponseEntity<?> AltaGenero(@Valid @RequestBody GeneroDto generoDto, BindingResult result) {
        Map<String, Object> validaciones = new HashMap<>();
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(fieldError -> validaciones.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(validaciones);
        }
        try {
            Genero genero = GeneroMapper.mapToEntity(generoDto);
            GeneroDto generoResponse = GeneroMapper.mapToDto(this.generoService.altaGenero(genero));
            return new ResponseEntity<>(generoResponse, HttpStatus.CREATED);
        } catch (YaExisteGeneroConMismoNombreException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGenero(@Valid @RequestBody GeneroDto generoDto, BindingResult result, @PathVariable Long id) {
        Map<String, Object> validaciones = new HashMap<>();
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(fieldError -> validaciones.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(validaciones);
        }
        try {
            Genero genero = GeneroMapper.mapToEntity(generoDto);
            GeneroDto generoResponse = GeneroMapper.mapToDto(this.generoService.updateGenero(genero, id));

            return new ResponseEntity<>(generoResponse, HttpStatus.OK);
        } catch (YaExisteGeneroConMismoNombreException | GeneroInexistenteException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
