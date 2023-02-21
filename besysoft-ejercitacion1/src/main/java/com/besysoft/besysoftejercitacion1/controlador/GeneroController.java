package com.besysoft.besysoftejercitacion1.controlador;

import com.besysoft.besysoftejercitacion1.dominio.Genero;
import com.besysoft.besysoftejercitacion1.service.interfaces.GeneroService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.CampoNombreEsObligatorioException;
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
    public ResponseEntity<?> AltaGenero(@Valid @RequestBody Genero genero, BindingResult result) throws CampoNombreEsObligatorioException {
        Map<String, Object> validaciones = new HashMap<>();
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(fieldError -> validaciones.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(validaciones);
        }
        try {
            return new ResponseEntity<>(this.generoService.altaGenero(genero), HttpStatus.CREATED);
        } catch (YaExisteGeneroConMismoNombreException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGenero(@RequestBody Genero genero, @PathVariable Long id) {
        try {
            return new ResponseEntity<>(this.generoService.updateGenero(genero, id), HttpStatus.OK);
        } catch (YaExisteGeneroConMismoNombreException | GeneroInexistenteException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
