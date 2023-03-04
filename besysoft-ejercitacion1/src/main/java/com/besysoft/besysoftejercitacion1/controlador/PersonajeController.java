package com.besysoft.besysoftejercitacion1.controlador;

import com.besysoft.besysoftejercitacion1.dominio.dto.PersonajeDto;
import com.besysoft.besysoftejercitacion1.dominio.dto.PersonajeDtoResponse;
import com.besysoft.besysoftejercitacion1.dominio.entity.Personaje;
import com.besysoft.besysoftejercitacion1.dominio.mapstruct.PersonajeMapper;
import com.besysoft.besysoftejercitacion1.service.interfaces.PersonajeService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.BuscarPorEdadOPorNombreException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.ElPersonajeExisteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.IdInexistente;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/personajes")
@AllArgsConstructor
public class PersonajeController {
    private final PersonajeService personajeService;
    private final PersonajeMapper personajeMapper;

    @GetMapping()
    public ResponseEntity<List<PersonajeDtoResponse>> devolverTodosLosPersonajes() {
        List<Personaje> personajes = this.personajeService.obtenerTodos();
        return new ResponseEntity<>(personajeMapper.mapLisToDto(personajes), HttpStatus.OK);
    }

    @GetMapping("/request-param")
    public ResponseEntity<?> buscarPersonajesPorNombreOrEdad(@RequestParam(defaultValue = "") String nombre, @RequestParam(defaultValue = "0") int edad) throws BuscarPorEdadOPorNombreException {
        List<Personaje> personajes = this.personajeService.buscarPersonajesPorNombreOrEdad(nombre, edad);
        return new ResponseEntity<>(personajeMapper.mapLisToDto(personajes), HttpStatus.OK);
    }

    @GetMapping("/edad")
    public Object buscarPersonajesPorRangoDeEdad(@RequestParam(defaultValue = "0") int desde, @RequestParam(defaultValue = "0") int hasta) {
        List<Personaje> personajes = this.personajeService.buscarPersonajesPorRangoDeEdad(desde, hasta);
        return personajeMapper.mapLisToDto(personajes);
    }

    @PostMapping()
    public ResponseEntity<?> AltaPersonaje(@Valid @RequestBody PersonajeDto personajeDto) throws ElPersonajeExisteException {
        Personaje personajeEntity = personajeMapper.mapToEntity(personajeDto);
        Personaje personajeSave = this.personajeService.altaPersonaje(personajeEntity);
        return new ResponseEntity<>(personajeMapper.mapToDtoResponse(personajeSave), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePersonaje(@Valid @RequestBody PersonajeDto personajeDto, @PathVariable Long id) throws IdInexistente, ElPersonajeExisteException {
        Personaje personajeEntity = personajeMapper.mapToEntity(personajeDto);
        Personaje personajeSave = this.personajeService.updatePersonaje(personajeEntity, id);
        return new ResponseEntity<>(personajeMapper.mapToDtoResponse(personajeSave), HttpStatus.OK);
    }
}
