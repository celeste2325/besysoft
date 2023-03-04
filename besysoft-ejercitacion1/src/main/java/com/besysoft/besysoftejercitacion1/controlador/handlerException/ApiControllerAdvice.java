package com.besysoft.besysoftejercitacion1.controlador.handlerException;

import com.besysoft.besysoftejercitacion1.dominio.dto.response.ExceptionDto;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class ApiControllerAdvice {
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto exceptionHandler(MethodArgumentNotValidException ex) {
        List<FieldError> errorList = ex.getBindingResult().getFieldErrors();
        Map<String, String> detalle = new HashMap<>();
        errorList.forEach(e -> detalle.put(e.getField(), e.getDefaultMessage()));
        return new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Validaciones", detalle);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto generoExistente(YaExisteGeneroConMismoNombreException ex) {
        log.error("ocurrio un error porque ya el genero existe", ex);
        return new ExceptionDto(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto idInexistente(IdInexistente ex) {
        log.error("ocurrio un error porque id del genero no existe", ex);
        return new ExceptionDto(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto PeliculaExistente(PeliculaExistenteConMismoTituloException ex) {
        log.error("ocurrio un error porque ya existe una pelicula con el mismo titulo", ex);
        return new ExceptionDto(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto buscarPersonajesPorNombreOrEdadError(BuscarPorEdadOPorNombreException ex) {
        log.error("ocurrio un error porque la busqueda es realizada por nombre Y edad. La misma debe realizarse por nombre O por edad", ex);
        return new ExceptionDto(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto PersonajeExistente(ElPersonajeExisteException ex) {
        log.error("ocurrio un error porque el personaje a crear ya existe", ex);
        return new ExceptionDto(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null
        );
    }


}
