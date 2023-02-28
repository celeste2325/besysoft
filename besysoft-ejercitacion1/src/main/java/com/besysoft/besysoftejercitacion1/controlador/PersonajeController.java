package com.besysoft.besysoftejercitacion1.controlador;

import com.besysoft.besysoftejercitacion1.dominio.dto.PersonajeDto;
import com.besysoft.besysoftejercitacion1.dominio.dto.PersonajeDtoResponse;
import com.besysoft.besysoftejercitacion1.dominio.entity.Personaje;
import com.besysoft.besysoftejercitacion1.dominio.mapper.PersonajeMapper;
import com.besysoft.besysoftejercitacion1.dominio.mapper.PersonajeResponseMapper;
import com.besysoft.besysoftejercitacion1.service.interfaces.PersonajeService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.BuscarPorEdadOPorNombreException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.ElPersonajeExisteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.NombreYEdadSonCamposObligatoriosException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.PersonajeInexistenteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/personajes")
public class PersonajeController {
    private final PersonajeService personajeService;

    public PersonajeController(PersonajeService personajeService) {
        this.personajeService = personajeService;
    }

    @GetMapping()
    public ResponseEntity<List<PersonajeDtoResponse>> devolverTodosLosPersonajes() {
        List<Personaje> personajes = this.personajeService.obtenerTodos();

        List<PersonajeDtoResponse> personajeDtoResponses = PersonajeResponseMapper.mapLisToDto(personajes);
        return new ResponseEntity<>(personajeDtoResponses, HttpStatus.OK);
    }

    @GetMapping("/request-param")
    public ResponseEntity<?> buscarPersonajesPorNombreOrEdad(@RequestParam(defaultValue = "") String nombre, @RequestParam(defaultValue = "0") int edad) {
        try {
            List<Personaje> personajes = this.personajeService.buscarPersonajesPorNombreOrEdad(nombre, edad);
            List<PersonajeDtoResponse> personajeDtoResponses = PersonajeResponseMapper.mapLisToDto(personajes);
            return new ResponseEntity<>(personajeDtoResponses, HttpStatus.OK);
        } catch (BuscarPorEdadOPorNombreException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/edad")
    public Object buscarPersonajesPorRangoDeEdad(@RequestParam(defaultValue = "0") int desde, @RequestParam(defaultValue = "0") int hasta) {
        List<Personaje> personajes = this.personajeService.buscarPersonajesPorRangoDeEdad(desde, hasta);
        List<PersonajeDtoResponse> personajeDtoResponses = PersonajeResponseMapper.mapLisToDto(personajes);
        return personajeDtoResponses;
    }

    @PostMapping()
    public ResponseEntity<?> AltaPersonaje(@Valid @RequestBody PersonajeDto personajeDto, BindingResult result) {
        Map<String, Object> validaciones = new HashMap<>();
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(fieldError -> validaciones.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(validaciones);
        }
        try {
            Personaje personajeEntity = PersonajeMapper.mapToEntity(personajeDto);
            Personaje personajeSave = this.personajeService.altaPersonaje(personajeEntity);
            PersonajeDtoResponse personajeResponse = PersonajeResponseMapper.mapToDtoResponse(personajeSave);
            return new ResponseEntity<>(personajeResponse, HttpStatus.CREATED);
        } catch (ElPersonajeExisteException | NombreYEdadSonCamposObligatoriosException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePersonaje(@Valid @RequestBody PersonajeDto personajeDto, BindingResult result, @PathVariable Long id) {
        Map<String, Object> validaciones = new HashMap<>();
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(fieldError -> validaciones.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(validaciones);
        }
        try {
            Personaje personajeEntity = PersonajeMapper.mapToEntity(personajeDto);
            Personaje personajeSave = this.personajeService.updatePersonaje(personajeEntity, id);
            PersonajeDtoResponse personajeResponse = PersonajeResponseMapper.mapToDtoResponse(personajeSave);

            return new ResponseEntity<>(personajeResponse, HttpStatus.OK);
        } catch (PersonajeInexistenteException | ElPersonajeExisteException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
