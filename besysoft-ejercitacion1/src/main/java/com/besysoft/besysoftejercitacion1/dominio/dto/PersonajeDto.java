package com.besysoft.besysoftejercitacion1.dominio.dto;

import lombok.Data;

import java.util.List;

@Data
public class PersonajeDto {
    private Long id;
    private String nombre;
    private int edad;
    private double peso;
    private String historia;
    private List<Pelicula_serieDto> peliculas_seriesDto;
}
