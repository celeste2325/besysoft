package com.besysoft.besysoftejercitacion1.controlador;

import com.besysoft.besysoftejercitacion1.dominio.dto.PersonajeDto;
import com.besysoft.besysoftejercitacion1.dominio.dto.PersonajeDtoResponse;
import com.besysoft.besysoftejercitacion1.dominio.entity.Personaje;
import com.besysoft.besysoftejercitacion1.dominio.mapstruct.PersonajeMapper;
import com.besysoft.besysoftejercitacion1.service.interfaces.PersonajeService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.BuscarPorEdadOPorNombreException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.ElPersonajeExisteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.PersonajeInexistenteException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@AllArgsConstructor
@Slf4j
public class PersonajeController {
    private final PersonajeService personajeService;
    private final PersonajeMapper personajeMapper;

    @GetMapping()
    public ResponseEntity<List<PersonajeDtoResponse>> devolverTodosLosPersonajes() {
        List<Personaje> personajes = this.personajeService.obtenerTodos();

        List<PersonajeDtoResponse> personajeDtoResponses = personajeMapper.mapLisToDto(personajes);
        return new ResponseEntity<>(personajeDtoResponses, HttpStatus.OK);
    }

    @GetMapping("/request-param")
    public ResponseEntity<?> buscarPersonajesPorNombreOrEdad(@RequestParam(defaultValue = "") String nombre, @RequestParam(defaultValue = "0") int edad) {
        try {
            List<Personaje> personajes = this.personajeService.buscarPersonajesPorNombreOrEdad(nombre, edad);
            List<PersonajeDtoResponse> personajeDtoResponses = personajeMapper.mapLisToDto(personajes);
            return new ResponseEntity<>(personajeDtoResponses, HttpStatus.OK);
        } catch (BuscarPorEdadOPorNombreException e) {
            log.error("ocurrio un error porque la busqueda es realizada por nombre Y edad. La misma debe realizarse por nombre O por edad", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/edad")
    public Object buscarPersonajesPorRangoDeEdad(@RequestParam(defaultValue = "0") int desde, @RequestParam(defaultValue = "0") int hasta) {
        List<Personaje> personajes = this.personajeService.buscarPersonajesPorRangoDeEdad(desde, hasta);
        List<PersonajeDtoResponse> personajeDtoResponses = personajeMapper.mapLisToDto(personajes);
        return personajeDtoResponses;
    }

    @PostMapping()
    public ResponseEntity<?> AltaPersonaje(@Valid @RequestBody PersonajeDto personajeDto, BindingResult result) {
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
            Personaje personajeEntity = personajeMapper.mapToEntity(personajeDto);
            Personaje personajeSave = this.personajeService.altaPersonaje(personajeEntity);
            PersonajeDtoResponse personajeResponse = personajeMapper.mapToDtoResponse(personajeSave);
            return new ResponseEntity<>(personajeResponse, HttpStatus.CREATED);
        } catch (ElPersonajeExisteException e) {
            log.error("ocurrio un error porque el personaje a crear ya existe", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePersonaje(@Valid @RequestBody PersonajeDto personajeDto, BindingResult result, @PathVariable Long id) {
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
            Personaje personajeEntity = personajeMapper.mapToEntity(personajeDto);
            Personaje personajeSave = this.personajeService.updatePersonaje(personajeEntity, id);
            PersonajeDtoResponse personajeResponse = personajeMapper.mapToDtoResponse(personajeSave);

            return new ResponseEntity<>(personajeResponse, HttpStatus.OK);
        } catch (PersonajeInexistenteException e) {
            log.error("ocurrio un error porque el personaje que se quiere modificar no existe", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ElPersonajeExisteException e) {
            log.error("ocurrio un error porque el nombre que se quiere asignar al personaje ya existe", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
