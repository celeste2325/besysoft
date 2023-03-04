package com.besysoft.besysoftejercitacion1.dominio.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PersonajeDto {
    private Long id;
    @NotNull
    @NotEmpty
    private String nombre;
    private int edad;
    private double peso;
    private String historia;
    private List<Pelicula_serieDto> peliculas_seriesDto;
}
