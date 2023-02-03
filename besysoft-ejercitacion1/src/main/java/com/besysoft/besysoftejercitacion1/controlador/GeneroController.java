package com.besysoft.besysoftejercitacion1.controlador;

import com.besysoft.besysoftejercitacion1.dominio.Genero;
import com.besysoft.besysoftejercitacion1.service.interfaces.GeneroService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.CampoNombreEsObligatorioException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.GeneroInexistenteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.YaExisteGeneroConMismoNombreException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/generos")
public class GeneroController {
    //TODO ya esta el refactor de genero, probarlo.
    private final GeneroService generoService;

    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    @PostMapping()
    public ResponseEntity<?> AltaGenero(@RequestBody Genero genero) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("app-info", "celeste@bootcamp.com");
        try {
            return new ResponseEntity<>(this.generoService.altaGenero(genero), headers, HttpStatus.CREATED);
        } catch (CampoNombreEsObligatorioException | YaExisteGeneroConMismoNombreException e) {
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
