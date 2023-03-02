package com.besysoft.besysoftejercitacion1.controlador;

import com.besysoft.besysoftejercitacion1.dominio.dto.GeneroDto;
import com.besysoft.besysoftejercitacion1.dominio.entity.Genero;
import com.besysoft.besysoftejercitacion1.dominio.mapstruct.GeneroMapper;
import com.besysoft.besysoftejercitacion1.service.interfaces.GeneroService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.GeneroInexistenteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.YaExisteGeneroConMismoNombreException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/generos")
@AllArgsConstructor
@Slf4j
public class GeneroController {

    private final GeneroService generoService;
    private final GeneroMapper generoMapper;

    @PostMapping()
    public ResponseEntity<?> AltaGenero(@Valid @RequestBody GeneroDto generoDto, BindingResult result) {
        Map<String, Object> validaciones = new HashMap<>();
        if (result.hasErrors()) {
            log.info("Ocurrio una validacion ");
            result.getFieldErrors().forEach(fieldError -> {
                log.info("Campo: " + fieldError.getField() + " validacion: " + fieldError.getDefaultMessage());
                validaciones.put(fieldError.getField(), fieldError.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(validaciones);
        }
        try {
            Genero genero = generoMapper.mapToEntity(generoDto);
            GeneroDto generoResponse = generoMapper.mapToDto(this.generoService.altaGenero(genero));
            return new ResponseEntity<>(generoResponse, HttpStatus.CREATED);
        } catch (YaExisteGeneroConMismoNombreException e) {
            log.error("ocurrio un error porque ya existe el genero que se quiere crear", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGenero(@Valid @RequestBody GeneroDto generoDto, BindingResult result, @PathVariable Long id) {
        Map<String, Object> validaciones = new HashMap<>();
        if (result.hasErrors()) {
            log.info("Ocurrio una validacion ");
            result.getFieldErrors().forEach(fieldError -> {
                log.info("Campo: " + fieldError.getField() + " validacion: " + fieldError.getDefaultMessage());
                validaciones.put(fieldError.getField(), fieldError.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(validaciones);
        }
        try {
            Genero genero = generoMapper.mapToEntity(generoDto);
            GeneroDto generoResponse = generoMapper.mapToDto(this.generoService.updateGenero(genero, id));

            return new ResponseEntity<>(generoResponse, HttpStatus.OK);
        } catch (YaExisteGeneroConMismoNombreException e) {
            log.error("ocurrio un error porque ya existe un genero con el mismo nombre que se quiere asignar", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (GeneroInexistenteException e) {
            log.error("ocurrio un error porque el genero que se quiere nmodificar no existe", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
