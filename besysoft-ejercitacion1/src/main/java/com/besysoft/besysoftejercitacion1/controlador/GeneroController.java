package com.besysoft.besysoftejercitacion1.controlador;

import com.besysoft.besysoftejercitacion1.dominio.dto.GeneroDto;
import com.besysoft.besysoftejercitacion1.dominio.entity.Genero;
import com.besysoft.besysoftejercitacion1.dominio.mapstruct.GeneroMapper;
import com.besysoft.besysoftejercitacion1.service.interfaces.GeneroService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.IdInexistente;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.YaExisteGeneroConMismoNombreException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/generos")
@AllArgsConstructor
public class GeneroController {

    private final GeneroService generoService;
    private final GeneroMapper generoMapper;

    @PostMapping()
    public ResponseEntity<?> AltaGenero(@Valid @RequestBody GeneroDto generoDto) throws YaExisteGeneroConMismoNombreException {
        Genero genero = generoMapper.mapToEntity(generoDto);
        return new ResponseEntity<>(generoMapper.mapToDto(this.generoService.altaGenero(genero)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGenero(@Valid @RequestBody GeneroDto generoDto, @PathVariable Long id) throws IdInexistente {
        Genero genero = generoMapper.mapToEntity(generoDto);
        return new ResponseEntity<>(generoMapper.mapToDto(this.generoService.updateGenero(genero, id)), HttpStatus.OK);
    }
}
